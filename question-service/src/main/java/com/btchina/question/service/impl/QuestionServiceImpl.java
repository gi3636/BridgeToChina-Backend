package com.btchina.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import com.btchina.core.exception.GlobalException;
import com.btchina.feign.clients.TagClient;
import com.btchina.feign.clients.UserClient;
import com.btchina.feign.model.form.tag.AddTagForm;
import com.btchina.feign.pojo.User;
import com.btchina.question.constant.QuestionConstant;
import com.btchina.question.entity.Question;
import com.btchina.question.entity.QuestionUserLike;
import com.btchina.question.mapper.QuestionMapper;
import com.btchina.question.mapper.es.QuestionRepository;
import com.btchina.question.model.doc.QuestionDoc;
import com.btchina.question.model.enums.QueryTypeEnum;
import com.btchina.question.model.form.AddQuestionForm;
import com.btchina.question.model.form.QuestionQueryForm;
import com.btchina.question.model.vo.QuestionVO;
import com.btchina.question.service.QuestionService;
import com.btchina.question.service.QuestionUserLikeService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 问答表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-02-01
 */
@Service
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagClient tagClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    @Lazy
    private QuestionUserLikeService questionUserLikeService;


    @Override
    public Boolean addQuestion(AddQuestionForm addQuestionForm, Long userId) {
        if (userId == null) {
            throw GlobalException.from("用户未登录");
        }
        // 1. 添加问题
        Random r = new Random();
        Question question = new Question();
        question.setTitle(addQuestionForm.getTitle());
        question.setContent(addQuestionForm.getContent());
        question.setUserId(userId);
        question.setFavoriteCount(0);
        question.setViewCount(0);
        question.setLikeCount(r.nextInt(1000));
        Boolean isSuccess = this.baseMapper.insert(question) > 0;
        // 2. 添加标签
        AddTagForm addTagForm = new AddTagForm();
        addTagForm.setId(question.getId());
        addTagForm.setTags(addQuestionForm.getTags());
        tagClient.addTag(addTagForm);
        QuestionDoc questionDoc = new QuestionDoc();
        BeanUtils.copyProperties(question, questionDoc);
        String tagString = "";
        for (String tag : addQuestionForm.getTags()) {
            tagString += tag + ",";
        }
        tagString = tagString.substring(0, tagString.length() - 1);
        questionDoc.setTags(tagString);
        // 3. 添加es文档
        if (isSuccess) {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.INSERT_KEY, questionDoc);
        }
        return isSuccess;
    }


    /**
     * 增加到es文档
     *
     * @param questionDoc
     * @return
     */
    @Override
    public void addEsDoc(QuestionDoc questionDoc) {
        try {
            QuestionDoc question = questionRepository.save(questionDoc);
            log.info("增加es文档成功: {} ", question);
        } catch (Exception e) {
            log.error("增加es文档失败: {} ", e.getMessage(), e);
        }
    }

    /**
     * 更新es文档
     *
     * @param questionDoc
     * @return
     */
    @Override
    public void updateEsDoc(QuestionDoc questionDoc) {
        try {
            QuestionDoc question = questionRepository.save(questionDoc);
            log.info("更新es文档成功: {} ", question);
        } catch (Exception e) {
            log.error("更新es文档失败: {} ", e.getMessage(), e);
        }
    }

    @Override
    public Boolean deleteQuestion(Long questionId, Long selfId) {
        if (selfId == null) {
            throw GlobalException.from("用户未登录");
        }
        Question question = this.baseMapper.selectById(questionId);

        if (question == null) {
            throw GlobalException.from("问题不存在");
        }

        if (!question.getUserId().equals(selfId)) {
            throw GlobalException.from("无权限删除");
        }
        // 1. 删除问题
        Boolean isSuccess = this.baseMapper.deleteById(questionId) > 0;
        DeleteForm deleteForm = new DeleteForm();
        deleteForm.setId(questionId);
        // 2. 删除标签与问题的关联
        tagClient.deleteTag(deleteForm);
        // 3. 删除es文档
        if (isSuccess) {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.DELETE_KEY, questionId);
        }
        return isSuccess;
    }

    @Override
    public void deleteEsDoc(Long id) {
        try {
            questionRepository.deleteById(id);
            log.info("删除es文档成功: {} ", id);
        } catch (Exception e) {
            log.error("删除es文档失败: {} ", e.getMessage(), e);
        }
    }

    @Override
    public SearchHits<QuestionDoc> queryEsQuestion(QuestionQueryForm questionQueryForm, Long selfId) {
        QueryTypeEnum queryTypeEnum = QueryTypeEnum.getQueryTypeEnum(questionQueryForm.getType());
        switch (queryTypeEnum) {
            case HOT:

                //QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
                //FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                //        new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("type", 3), ScoreFunctionBuilders.weightFactorFunction(100)),
                //        new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.termQuery("type", 2), ScoreFunctionBuilders.weightFactorFunction(1)),
                //        new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("type", 1), ScoreFunctionBuilders.weightFactorFunction(1))
                //};
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                //ScoreFunctionBuilder<?> scoreFunctionBuilder = ScoreFunctionBuilders.fieldValueFactorFunction("likeCount").modifier(FieldValueFactorFunction.Modifier.LN1P).factor(0.1f);
                //FunctionScoreQueryBuilder query = QueryBuilders.functionScoreQuery(boolQueryBuilder,scoreFunctionBuilder).boostMode(CombineFunction.SUM);

                FieldValueFactorFunctionBuilder fieldQuery = new FieldValueFactorFunctionBuilder(
                        "likeCount");
                // 额外分数=log(1+score)
                fieldQuery.factor(0.1f);
                fieldQuery.modifier(FieldValueFactorFunction.Modifier.LOG1P);
                // 最终分数=_score+额外分数
                FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
                        .functionScoreQuery(boolQueryBuilder, fieldQuery)
                        .boostMode(CombineFunction.SUM);
                //根据分值倒序排列

                boolQueryBuilder.must().add(QueryBuilders.multiMatchQuery("测试", "title", "content"));
                boolQueryBuilder.must().add(QueryBuilders.rangeQuery("createdTime").gte(1675853174178L));
                //boolQueryBuilder.must().add(scoreFunctionBuilder);
                long now = System.currentTimeMillis() / 1000L;
                long daySecond = 60 * 60 * 24;
                long dayTime = now - (now + 8 * 3600) % daySecond;
                log.info("dayTime:{}", dayTime);
                //指定多个field
                NativeSearchQuery query1 = new NativeSearchQueryBuilder()
                        //.withQuery(queryBuilder)
                        .withQuery(functionScoreQueryBuilder)
                        //.withPageable(page)
                        //排序
                        .withSort(SortBuilders.fieldSort("_score").order(SortOrder.DESC))
                        //.withSort(sort)
                        .build();
                //3.执行查询
                SearchHits<QuestionDoc> result = elasticsearchRestTemplate.search(query1, QuestionDoc.class);
                return result;
            //查询对象
            case MY:
                log.info("查询我的问题");
                break;
        }
        return null;

    }

    @Override
    public PageResult<QuestionVO> queryQuestion(QuestionQueryForm questionQueryForm, Long selfId) {
        QueryTypeEnum queryTypeEnum = QueryTypeEnum.getQueryTypeEnum(questionQueryForm.getType());
        switch (queryTypeEnum) {
            case HOT:
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                FieldValueFactorFunctionBuilder fieldQuery = new FieldValueFactorFunctionBuilder("likeCount")
                        .modifier(FieldValueFactorFunction.Modifier.LOG1P)
                        .factor(0.1f);

                // 最终分数=_score+额外分数
                FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
                        .functionScoreQuery(boolQueryBuilder, fieldQuery)
                        .boostMode(CombineFunction.SUM);

                if (questionQueryForm.getDate() != null) {
                    RangeQueryBuilder timeRangeQuery = QueryBuilders.rangeQuery("createdTime")
                            //.gte(LocalDateTime.now(ZoneOffset.UTC).minusDays(3))
                            //.lte(LocalDateTime.now(ZoneOffset.UTC));
                            .gte(questionQueryForm.getDate());
                    boolQueryBuilder.filter(timeRangeQuery);
                }

                Pageable page = PageRequest.of(questionQueryForm.getCurrentPage() - 1, questionQueryForm.getPageSize());
                NativeSearchQuery query = new NativeSearchQueryBuilder()
                        .withQuery(functionScoreQueryBuilder)
                        .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                        .withPageable(page)
                        .build();

                // 3.执行查询
                SearchHits<QuestionDoc> result = elasticsearchRestTemplate.search(query, QuestionDoc.class);
                List<QuestionVO> questionVOList = new ArrayList<>();
                for (SearchHit<QuestionDoc> searchHit : result.getSearchHits()) {
                    QuestionVO questionVO = new QuestionVO();
                    BeanUtils.copyProperties(searchHit.getContent(), questionVO);
                    QuestionUserLike userLike = questionUserLikeService.getQuestionUserLike(questionVO.getId(), selfId);
                    if (userLike != null) {
                        questionVO.setLikeStatus(userLike.getStatus());
                    } else {
                        questionVO.setLikeStatus(0);
                    }
                    User user =userClient.findById(questionVO.getUserId());
                    questionVO.setNickname(user.getNickname());
                    questionVOList.add(questionVO);
                }

                //封装分页结果
                PageResult<QuestionVO> pageResult = new PageResult<>();
                pageResult.setTotal(result.getTotalHits());
                pageResult.setList(questionVOList);
                pageResult.setCurrentPage(questionQueryForm.getCurrentPage());
                pageResult.setPageSize(questionQueryForm.getPageSize());
                pageResult.setTotalPage((int) Math.ceil((double) result.getTotalHits() / questionQueryForm.getPageSize()));
                return pageResult;
            case MY:
                log.info("查询我的问题");
                break;
        }
        return null;

    }
}
