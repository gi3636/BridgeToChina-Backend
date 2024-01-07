package com.btchina.content.tag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.core.api.PageResult;
import com.btchina.content.tag.model.Tag;
import com.btchina.content.tag.manager.TagManager;
import com.btchina.content.tag.mapper.TagMapper;
import com.btchina.content.tag.mapper.es.TagRepository;
import com.btchina.content.tag.model.doc.TagDoc;
import com.btchina.content.tag.feign.qo.TagAddQO;
import com.btchina.content.tag.feign.qo.TagQueryQO;
import com.btchina.content.tag.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-02-06
 */
@Service
@Slf4j
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    @Value("${secret.openai}")
    private String openAiSecret;

    @Autowired
    TagManager tagManager;

    @Autowired
    TagRepository tagRepository;

    /**
     * 单纯的添加标签，没有关联
     *
     * @param tagAddQO
     * @return
     */
    @Override
    public Boolean addTag(TagAddQO tagAddQO) {
        List<String> tags = tagAddQO.getTags();
        // 1. 标签是否存在
        tagManager.addTag(tags);
        return true;
    }


    @Override
    public Tag selectOne(String name) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName, name);
        return getOne(queryWrapper);
    }

    @Override
    public void addEsDoc(TagDoc tagDoc) {
        try {
            TagDoc tag = tagRepository.save(tagDoc);
            log.info("增加es文档成功: {} ", tag);
        } catch (Exception e) {
            log.error("增加es文档失败: {} ", e.getMessage(), e);
        }
    }

    @Override
    public void updateEsDoc(TagDoc tagDoc) {
        try {
            TagDoc tag = tagRepository.save(tagDoc);
            log.info("更新es文档成功: {} ", tag);
        } catch (Exception e) {
            log.error("更新es文档失败: {} ", e.getMessage(), e);
        }
    }

    @Override
    public PageResult<Tag> queryTags(TagQueryQO tagQueryQO) {
        if (tagQueryQO.getKeyword().isEmpty()){
            return tagManager.queryRecommendTags(tagQueryQO);
        }else {
            return tagManager.querySearchTags(tagQueryQO);
        }
    }

    @Override
    public String autoComplete(String text) {
        String API_KEY = openAiSecret;
        String prompt = "请根据以下句子给出5个相关标签，用逗号分隔返回结果:\n" + text;
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
}
