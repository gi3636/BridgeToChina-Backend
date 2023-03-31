package com.btchina.content.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.core.api.PageResult;
import com.btchina.content.entity.Tag;
import com.btchina.content.model.form.QueryTagForm;
import com.btchina.content.constant.TagConstant;
import com.btchina.content.mapper.TagMapper;
import com.btchina.content.model.doc.TagDoc;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagManager extends ServiceImpl<TagMapper, Tag> {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 添加标签
     *
     * @param tagList
     * @return
     */
    public Boolean addTag(List<String> tagList) {
        // 1. 标签是否存在
        for (String name : tagList) {
            LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Tag::getName, name);
            // 2. 不存在则创建
            Tag tag = getOne(queryWrapper);
            if (tag == null) {
                tag = new Tag();
                tag.setCount(1);
                tag.setName(name);
                save(tag);
                rabbitTemplate.convertAndSend(TagConstant.EXCHANGE_NAME, TagConstant.INSERT_KEY, tag);
            } else {
                // 3. 存在则更新
                tag.setCount(tag.getCount() + 1);
                updateById(tag);
                rabbitTemplate.convertAndSend(TagConstant.EXCHANGE_NAME, TagConstant.UPDATE_KEY, tag);
            }
        }
        return true;
    }

    public PageResult<Tag> queryRecommendTags(QueryTagForm queryTagForm) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        FieldValueFactorFunctionBuilder fieldQuery = new FieldValueFactorFunctionBuilder("count")
                .modifier(FieldValueFactorFunction.Modifier.LOG1P)
                .factor(0.1f);

        // 最终分数=_score+额外分数
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
                .functionScoreQuery(boolQueryBuilder, fieldQuery)
                .boostMode(CombineFunction.SUM);


        Pageable page = PageRequest.of(queryTagForm.getCurrentPage() - 1, queryTagForm.getPageSize());
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(functionScoreQueryBuilder)
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .withPageable(page)
                .build();

        // 3.执行查询
        SearchHits<TagDoc> result = elasticsearchRestTemplate.search(query, TagDoc.class);
        List<Tag> tagList = convertSearchHitsToTagList(result);
        //封装分页结果
        return convertToPageResult(result, queryTagForm, tagList);
    }

    public PageResult<Tag> querySearchTags(QueryTagForm queryTagForm) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery("name", queryTagForm.getKeyword()));

        Pageable page = PageRequest.of(queryTagForm.getCurrentPage() - 1, queryTagForm.getPageSize());
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(page)
                .build();

        // 3.执行查询
        SearchHits<TagDoc> result = elasticsearchRestTemplate.search(query, TagDoc.class);
        List<Tag> tagList = convertSearchHitsToTagList(result);
        //封装分页结果
        return convertToPageResult(result, queryTagForm, tagList);
    }

    public List<Tag> convertSearchHitsToTagList(SearchHits<TagDoc> result) {
        List<Tag> tagList = new ArrayList<>();
        for (SearchHit<TagDoc> searchHit : result.getSearchHits()) {
            Tag tag = new Tag();
            BeanUtils.copyProperties(searchHit.getContent(), tag);
            tagList.add(tag);
        }
        return tagList;
    }

    public PageResult<Tag> convertToPageResult(SearchHits<TagDoc> result, QueryTagForm queryTagForm, List<Tag> tagList) {
        PageResult<Tag> pageResult = new PageResult<>();
        pageResult.setTotal(result.getTotalHits());
        pageResult.setList(tagList);
        pageResult.setCurrentPage(queryTagForm.getCurrentPage());
        pageResult.setPageSize(queryTagForm.getPageSize());
        pageResult.setTotalPage((int) Math.ceil((double) result.getTotalHits() / queryTagForm.getPageSize()));
        return pageResult;
    }
}
