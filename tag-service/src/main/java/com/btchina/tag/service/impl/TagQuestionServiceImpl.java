package com.btchina.tag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.tag.entity.TagQuestion;
import com.btchina.tag.manager.TagManager;
import com.btchina.tag.mapper.TagQuestionMapper;
import com.btchina.tag.model.form.AddTagForm;
import com.btchina.tag.service.TagQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.tag.service.TagService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标签问题表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-02-06
 */
@Service
public class TagQuestionServiceImpl extends ServiceImpl<TagQuestionMapper, TagQuestion> implements TagQuestionService {
    @Autowired
    TagManager tagManager;

    @Autowired
    TagService tagService;


    @Override
    public Boolean addTag(AddTagForm addTagForm) {
        List<String> tags = addTagForm.getTags();
        Long questionId = addTagForm.getId();
        // 1. 标签是否存在
        tagManager.addTag(tags);
        // 2. 保存标签问题关系
        for (String tag : tags) {
            TagQuestion tagQuestion = new TagQuestion();
            tagQuestion.setQuestionId(questionId);
            tagQuestion.setTagId(tagService.selectOne(tag).getId());
            save(tagQuestion);
        }
        return true;
    }
}
