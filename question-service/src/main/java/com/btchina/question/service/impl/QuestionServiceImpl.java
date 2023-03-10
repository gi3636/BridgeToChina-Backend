package com.btchina.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.entity.User;
import com.btchina.feign.clients.QuestionClient;
import com.btchina.feign.clients.TagClient;
import com.btchina.feign.clients.UserClient;
import com.btchina.model.form.tag.AddTagForm;
import com.btchina.model.form.tag.EditQuestionTagForm;
import com.btchina.model.vo.answer.AnswerVO;
import com.btchina.question.constant.QuestionConstant;
import com.btchina.question.entity.Answer;
import com.btchina.question.entity.Question;
import com.btchina.question.entity.QuestionUserFavorite;
import com.btchina.question.entity.QuestionUserLike;
import com.btchina.question.mapper.QuestionMapper;
import com.btchina.question.mapper.es.QuestionRepository;
import com.btchina.question.model.doc.QuestionDoc;
import com.btchina.question.model.enums.QueryTypeEnum;
import com.btchina.question.model.form.*;
import com.btchina.question.model.vo.QuestionVO;
import com.btchina.question.service.AnswerService;
import com.btchina.question.service.QuestionService;
import com.btchina.question.service.QuestionUserFavoriteService;
import com.btchina.question.service.QuestionUserLikeService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 * ????????? ???????????????
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
    private QuestionClient questionClient;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    @Lazy
    private QuestionUserLikeService questionUserLikeService;

    @Autowired
    @Lazy
    private QuestionUserFavoriteService questionUserFavoriteService;

    @Autowired
    private AnswerService answerService;

    @Value("${secret.openai}")
    private String openAiSecret;
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public Boolean addQuestion(AddQuestionForm addQuestionForm, Long userId) {
        if (userId == null) {
            throw GlobalException.from(ResultCode.UNAUTHORIZED);
        }
        // 1. ????????????
        Random r = new Random();
        Question question = new Question();
        question.setTitle(addQuestionForm.getTitle());
        question.setContent(addQuestionForm.getContent());
        question.setUserId(userId);
        question.setFavoriteCount(0);
        question.setViewCount(0);
        question.setAnswerCount(0);
        question.setIsPublic(true);
        question.setLikeCount(r.nextInt(1000));
        if (addQuestionForm.getImages() != null) {
            String images = String.join(",", addQuestionForm.getImages());
            question.setImages(images);
        }
        Boolean isSuccess = this.baseMapper.insert(question) > 0;

        // 2. ????????????
        AddTagForm addTagForm = new AddTagForm();
        addTagForm.setId(question.getId());
        addTagForm.setTags(addQuestionForm.getTags());
        tagClient.addTag(addTagForm);

        // 3. ??????es??????
        QuestionDoc questionDoc = new QuestionDoc();
        BeanUtils.copyProperties(question, questionDoc);
        String tagString = String.join(",", addQuestionForm.getTags());
        questionDoc.setTags(tagString);
        // 3. ??????es??????
        if (isSuccess) {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.INSERT_KEY, questionDoc);
        }
        return isSuccess;
    }


    /**
     * ?????????es??????
     *
     * @param questionDoc
     * @return
     */
    @Override
    public void addEsDoc(QuestionDoc questionDoc) {
        try {
            QuestionDoc question = questionRepository.save(questionDoc);
            log.info("??????es????????????: {} ", question);
        } catch (Exception e) {
            log.error("??????es????????????: {} ", e.getMessage(), e);
        }
    }

    /**
     * ??????es??????
     *
     * @param questionDoc
     * @return
     */
    @Override
    public void updateEsDoc(QuestionDoc questionDoc) {
        try {
            QuestionDoc question = questionRepository.save(questionDoc);
            log.info("??????es????????????: {} ", question);
        } catch (Exception e) {
            log.error("??????es????????????: {} ", e.getMessage(), e);
        }
    }

    @Override
    public Boolean deleteQuestion(Long questionId, Long selfId) {
        if (selfId == null) {
            throw GlobalException.from(ResultCode.UNAUTHORIZED);
        }
        Question question = this.baseMapper.selectById(questionId);

        if (question == null) {
            throw GlobalException.from("???????????????");
        }

        if (!question.getUserId().equals(selfId)) {
            throw GlobalException.from("???????????????");
        }
        // 1. ????????????
        Boolean isSuccess = this.baseMapper.deleteById(questionId) > 0;
        DeleteForm deleteForm = new DeleteForm();
        deleteForm.setId(questionId);
        // 2. ??????????????????????????????
        tagClient.deleteTag(deleteForm);
        // 3. ??????es??????
        if (isSuccess) {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.DELETE_KEY, questionId);
        }
        return isSuccess;
    }

    @Override
    public void deleteEsDoc(Long id) {
        try {
            questionRepository.deleteById(id);
            log.info("??????es????????????: {} ", id);
        } catch (Exception e) {
            log.error("??????es????????????: {} ", e.getMessage(), e);
        }
    }

    @Override
    public void updateFieldEsDoc(Long id, String field, Object value) {
        try {
            Document document = Document.create();
            document.put(field, value);
            UpdateResponse response = elasticsearchRestTemplate.update(UpdateQuery.builder(id.toString()).withDocument(document).build(), IndexCoordinates.of(QuestionConstant.INDEX));
            System.out.println("response = " + response);
            log.info("??????es????????????: {} ", id);
        } catch (Exception e) {
            log.error("??????es????????????: {} ", e.getMessage(), e);
        }
    }

    @Override
    public QuestionDoc getEsDoc(Long id) {
        return questionRepository.findById(id.toString());
    }

    @Override
    public Boolean editQuestion(EditQuestionForm editQuestionForm, Long selfId) {
        if (selfId == null) {
            throw GlobalException.from(ResultCode.UNAUTHORIZED);
        }
        QuestionDoc questionDoc = questionRepository.findById(editQuestionForm.getId().toString());
        if (questionDoc == null) {
            throw GlobalException.from("???????????????");
        }
        if (!questionDoc.getUserId().equals(selfId)) {
            throw GlobalException.from("???????????????");
        }
        questionDoc.setTitle(editQuestionForm.getTitle());
        questionDoc.setContent(editQuestionForm.getContent());
        String tagStr = String.join(",", editQuestionForm.getTags());
        if (editQuestionForm.getImages() != null) {
            String images = String.join(",", editQuestionForm.getImages());
            questionDoc.setImages(images);
        }
        questionDoc.setIsPublic(editQuestionForm.getIsPublic());
        questionDoc.setTags(tagStr);
        Question question = new Question();
        BeanUtils.copyProperties(questionDoc, question);
        Boolean isSuccess = this.baseMapper.updateById(question) > 0;

        // 1. ????????????
        EditQuestionTagForm editQuestionTagForm = new EditQuestionTagForm();
        editQuestionTagForm.setId(editQuestionForm.getId());
        editQuestionTagForm.setTags(editQuestionForm.getTags());
        tagClient.editQuestionTags(editQuestionTagForm);

        // 2. ??????es??????
        if (isSuccess) {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.UPDATE_KEY, questionDoc);
        }
        return isSuccess;
    }

    /**
     * ????????????????????????
     *
     * @param questionId
     */
    @Override
    public void increaseAnswerCount(Long questionId) {
        QuestionDoc questionDoc = getEsDoc(questionId);
        if (questionDoc == null) {
            return;
        }
        if (questionDoc.getAnswerCount() == null) {
            questionDoc.setAnswerCount(0);
        } else {
            questionDoc.setAnswerCount(questionDoc.getAnswerCount() + 1);
        }
        updateEsDoc(questionDoc);
        Question question = new Question();
        BeanUtils.copyProperties(questionDoc, question);
        this.baseMapper.updateById(question);
    }

    /**
     * ????????????????????????
     *
     * @param questionId
     */
    @Override
    public void decreaseAnswerCount(Long questionId) {
        QuestionDoc questionDoc = getEsDoc(questionId);
        if (questionDoc == null) {
            return;
        }
        if (questionDoc.getAnswerCount() == null) {
            questionDoc.setAnswerCount(0);
        } else {
            questionDoc.setAnswerCount(questionDoc.getAnswerCount() - 1);
        }
        updateEsDoc(questionDoc);
        Question question = new Question();
        BeanUtils.copyProperties(questionDoc, question);
        this.baseMapper.updateById(question);
    }

    @Override
    public Boolean setBestAnswer(QuestionSetAnswerForm questionSetAnswerForm, Long userId) {
        if (userId == null) {
            throw GlobalException.from(ResultCode.UNAUTHORIZED);
        }
        QuestionDoc questionDoc = getEsDoc(questionSetAnswerForm.getQuestionId());
        if (questionDoc == null) {
            throw GlobalException.from("???????????????");
        }
        if (!questionDoc.getUserId().equals(userId)) {
            throw GlobalException.from("???????????????????????????");
        }
        Answer answer = answerService.getById(questionSetAnswerForm.getAnswerId());
        if (answer == null) {
            throw GlobalException.from("???????????????");
        }
        //???????????????????????????????????????????????????
        if (questionDoc.getBestAnswerId() != null) {
            Answer oldAnswer = answerService.getById(questionDoc.getBestAnswerId());
            if (oldAnswer != null) {
                oldAnswer.setIsBest(0);
                answerService.updateById(oldAnswer);
            }
        }
        //??????????????????
        answer.setIsBest(1);
        answerService.updateById(answer);

        // 1. ??????????????????
        questionDoc.setBestAnswerId(questionSetAnswerForm.getAnswerId());
        Question question = new Question();
        BeanUtils.copyProperties(questionDoc, question);

        Boolean isSuccess = this.baseMapper.updateById(question) > 0;
        // 2. ??????es??????
        if (isSuccess) {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.UPDATE_KEY, questionDoc);
        }


        return isSuccess;
    }

    @Override
    public QuestionVO getVObyId(Long id, Long userId) {
        QuestionVO questionVO = new QuestionVO();
        QuestionDoc questionDoc = getEsDoc(id);
        if (questionDoc == null) {
            throw GlobalException.from("???????????????");
        }
        User user = userClient.findById(questionDoc.getUserId());
        BeanUtils.copyProperties(questionDoc, questionVO);
        //?????????????????????
        if (questionDoc.getTags() != null) {
            List<String> tags = Arrays.asList(questionDoc.getTags().split(","));
            questionVO.setTags(tags);
        }
        if (questionDoc.getImages() != null) {
            List<String> images = Arrays.asList(questionDoc.getImages().split(","));
            questionVO.setImages(images);
        }

        LambdaQueryWrapper<QuestionUserLike> questionUserLikeWrapper = new LambdaQueryWrapper<QuestionUserLike>();
        if (userId != null) {
            questionUserLikeWrapper.eq(QuestionUserLike::getQuestionId, id);
            questionUserLikeWrapper.eq(QuestionUserLike::getUserId, userId);
            QuestionUserLike userLike = questionUserLikeService.getOne(questionUserLikeWrapper);
            if (userLike != null) {
                questionVO.setLikeStatus(userLike.getStatus());
            }
        }
        questionVO.setAvatar(user.getAvatar());
        questionVO.setNickname(user.getNickname());
        return questionVO;
    }

    @Override
    public PageResult<QuestionVO> searchQuestion(QuestionSearchForm questionSearchForm, Long selfId) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must().add(QueryBuilders.multiMatchQuery(questionSearchForm.getKeyword(), "searchContent", "title", "content"));
        Pageable page = PageRequest.of(questionSearchForm.getCurrentPage() - 1, questionSearchForm.getPageSize());
        //????????????field
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(page)
                .withSort(SortBuilders.fieldSort("_score").order(SortOrder.DESC))
                .build();
        //3.????????????
        SearchHits<QuestionDoc> result = elasticsearchRestTemplate.search(query, QuestionDoc.class);
        List<QuestionVO> questionVOList = convertSearchHits(result, selfId);
        PageResult<QuestionVO> pageResult = new PageResult<>();
        pageResult.setTotal(result.getTotalHits());
        pageResult.setList(questionVOList);
        pageResult.setCurrentPage(questionSearchForm.getCurrentPage());
        pageResult.setPageSize(questionSearchForm.getPageSize());
        pageResult.setTotalPage((int) Math.ceil((double) result.getTotalHits() / questionSearchForm.getPageSize()));
        return pageResult;
    }

    @Override
    public void increaseFavouriteCount(Long questionId) {
        QuestionDoc questionDoc = getEsDoc(questionId);
        if (questionDoc == null) {
            return;
        }
        if (questionDoc.getFavoriteCount() == null) {
            questionDoc.setFavoriteCount(0);
        } else {
            questionDoc.setFavoriteCount(questionDoc.getFavoriteCount() + 1);
        }
        updateEsDoc(questionDoc);
        Question question = new Question();
        BeanUtils.copyProperties(questionDoc, question);
        this.baseMapper.updateById(question);
    }

    public void decreaseFavouriteCount(Long questionId) {
        QuestionDoc questionDoc = getEsDoc(questionId);
        if (questionDoc == null) {
            return;
        }
        if (questionDoc.getFavoriteCount() == null) {
            questionDoc.setFavoriteCount(0);
        } else {
            questionDoc.setFavoriteCount(questionDoc.getFavoriteCount() - 1);
        }
        updateEsDoc(questionDoc);
        Question question = new Question();
        BeanUtils.copyProperties(questionDoc, question);
        this.baseMapper.updateById(question);
    }

    @Override
    public Boolean addView(Long id) {
        QuestionDoc questionDoc = getEsDoc(id);
        if (questionDoc == null) {
            throw GlobalException.from(ResultCode.QUESTION_NOT_EXIST);
        }
        if (questionDoc.getViewCount() == null) {
            questionDoc.setViewCount(0);
        } else {
            questionDoc.setViewCount(questionDoc.getViewCount() + 1);
        }
        updateEsDoc(questionDoc);
        updateQuestion(questionDoc);
        return true;
    }

    @Override
    public Boolean cancelBestAnswer(QuestionSetAnswerForm questionSetAnswerForm, Long userId) {
        if (userId == null) {
            throw GlobalException.from(ResultCode.UNAUTHORIZED);
        }
        QuestionDoc questionDoc = getEsDoc(questionSetAnswerForm.getQuestionId());
        if (questionDoc == null) {
            throw GlobalException.from(ResultCode.QUESTION_NOT_EXIST);
        }
        if (!questionDoc.getUserId().equals(userId)) {
            throw GlobalException.from(ResultCode.UNAUTHORIZED);
        }
        Answer answer = answerService.getById(questionDoc.getBestAnswerId());
        if (answer == null) {
            throw GlobalException.from(ResultCode.ANSWER_NOT_EXIST);
        }
        answer.setIsBest(0);
        answerService.updateById(answer);
        questionDoc.setBestAnswerId(null);
        updateEsDoc(questionDoc);
        updateQuestion(questionDoc);
        return true;
    }

    @Override
    public String generateTitle(String text) {
        String API_KEY = openAiSecret;
        String prompt = "?????????????????????????????????????????????#????????????# ??????????????????????????????:\n" + text;
        String model = "text-davinci-003";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", 100);
        requestBody.put("n", 1);
        requestBody.put("temperature", 0.5);
        RequestBody body = RequestBody.create(JSON, new Gson().toJson(requestBody));
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .post(body)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .build();
        String responseStr = "";
        try {
            Response response = client.newCall(request).execute();
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStr;
    }


    public void updateQuestion(QuestionDoc questionDoc) {
        Question question = new Question();
        BeanUtils.copyProperties(questionDoc, question);
        this.baseMapper.updateById(question);
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
                // ????????????=log(1+score)
                fieldQuery.factor(0.1f);
                fieldQuery.modifier(FieldValueFactorFunction.Modifier.LOG1P);
                // ????????????=_score+????????????
                FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
                        .functionScoreQuery(boolQueryBuilder, fieldQuery)
                        .boostMode(CombineFunction.SUM);
                //????????????????????????

                boolQueryBuilder.must().add(QueryBuilders.multiMatchQuery("??????", "title", "content"));
                boolQueryBuilder.must().add(QueryBuilders.rangeQuery("createdTime").gte(1675853174178L));
                //boolQueryBuilder.must().add(scoreFunctionBuilder);
                long now = System.currentTimeMillis() / 1000L;
                long daySecond = 60 * 60 * 24;
                long dayTime = now - (now + 8 * 3600) % daySecond;
                log.info("dayTime:{}", dayTime);
                //????????????field
                NativeSearchQuery query1 = new NativeSearchQueryBuilder()
                        //.withQuery(queryBuilder)
                        .withQuery(functionScoreQueryBuilder)
                        //.withPageable(page)
                        //??????
                        .withSort(SortBuilders.fieldSort("_score").order(SortOrder.DESC))
                        //.withSort(sort)
                        .build();
                //3.????????????
                SearchHits<QuestionDoc> result = elasticsearchRestTemplate.search(query1, QuestionDoc.class);
                return result;
            //????????????
            case MY:
                log.info("??????????????????");
                break;
        }
        return null;

    }

    @Override
    public PageResult<QuestionVO> queryQuestion(QuestionQueryForm questionQueryForm, Long selfId) {
        QueryTypeEnum queryTypeEnum = QueryTypeEnum.getQueryTypeEnum(questionQueryForm.getType());
        switch (queryTypeEnum) {
            case HOT:
                log.info("??????????????????");
                return queryHotQuestion(questionQueryForm, selfId);
            case NEW:
                log.info("??????????????????");
                return queryNewQuestion(questionQueryForm, selfId);
        }
        return null;
    }

    public  PageResult<QuestionVO> queryHotQuestion( QuestionQueryForm questionQueryForm, Long selfId) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        FieldValueFactorFunctionBuilder fieldQuery = new FieldValueFactorFunctionBuilder("likeCount")
                .modifier(FieldValueFactorFunction.Modifier.LOG1P)
                .factor(0.1f);

        // ????????????=_score+????????????
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

        // 3.????????????
        SearchHits<QuestionDoc> result = elasticsearchRestTemplate.search(query, QuestionDoc.class);
        List<QuestionVO> questionVOList = convertSearchHits(result, selfId);
        //??????????????????
        return convertToPageResult(result, questionVOList, questionQueryForm);
    }

    /**
     * ??????????????????
     * @param questionQueryForm
     * @param selfId
     * @return
     */
    public PageResult<QuestionVO> queryNewQuestion(QuestionQueryForm questionQueryForm, Long selfId) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        Pageable page = PageRequest.of(questionQueryForm.getCurrentPage() - 1, questionQueryForm.getPageSize());
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withSort(SortBuilders.fieldSort("createdTime").order(SortOrder.DESC))
                .withPageable(page)
                .build();
        // 3.????????????
        SearchHits<QuestionDoc> result = elasticsearchRestTemplate.search(query, QuestionDoc.class);
        List<QuestionVO> questionVOList = convertSearchHits(result, selfId);
        //??????????????????
        return convertToPageResult(result, questionVOList, questionQueryForm);
    }

    public PageResult<QuestionVO> convertToPageResult(SearchHits<QuestionDoc> result, List<QuestionVO> questionVOList, QuestionQueryForm questionQueryForm) {
        PageResult<QuestionVO> pageResult = new PageResult<>();
        pageResult.setTotal(result.getTotalHits());
        pageResult.setList(questionVOList);
        pageResult.setCurrentPage(questionQueryForm.getCurrentPage());
        pageResult.setPageSize(questionQueryForm.getPageSize());
        pageResult.setTotalPage((int) Math.ceil((double) result.getTotalHits() / questionQueryForm.getPageSize()));
        return pageResult;
    }


    public List<QuestionVO> convertSearchHits(SearchHits<QuestionDoc> result, Long selfId) {
        List<QuestionVO> questionVOList = new ArrayList<>();
        for (SearchHit<QuestionDoc> searchHit : result.getSearchHits()) {
            QuestionVO questionVO = new QuestionVO();
            BeanUtils.copyProperties(searchHit.getContent(), questionVO);
            QuestionUserLike userLike = null;
            //????????????????????????,??????selfId?????????????????????
            if (selfId != null) {
                userLike = questionUserLikeService.getQuestionUserLike(questionVO.getId(), selfId);
            }
            if (userLike != null) {
                questionVO.setLikeStatus(userLike.getStatus());
            } else {
                questionVO.setLikeStatus(0);
            }

            //????????????????????????
            QuestionUserFavorite userFavorite = null;
            if (selfId != null) {
                userFavorite = questionUserFavoriteService.getQuestionUserFavorite(questionVO.getId(), selfId);
            }
            if (userFavorite != null) {
                questionVO.setFavoriteStatus(userFavorite.getStatus());
            } else {
                questionVO.setFavoriteStatus(0);
            }

            if (searchHit.getContent().getBestAnswerId() != null) {
                AnswerVO answer = questionClient.findAnswerVOById(searchHit.getContent().getBestAnswerId());
                questionVO.setBestAnswer(answer);
            }
            if (searchHit.getContent().getImages() != null) {
                questionVO.setImages(Arrays.asList(searchHit.getContent().getImages().split(",")));
            } else {
                questionVO.setImages(new ArrayList<>());
            }
            if (searchHit.getContent().getTags() != null) {
                questionVO.setTags(Arrays.asList(searchHit.getContent().getTags().split(",")));
            } else {
                questionVO.setTags(new ArrayList<>());
            }
            User user = userClient.findById(questionVO.getUserId());
            questionVO.setNickname(user.getNickname());
            questionVO.setAvatar(user.getAvatar());
            questionVOList.add(questionVO);
        }
        return questionVOList;
    }
}
