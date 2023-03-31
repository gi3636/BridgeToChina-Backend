package com.btchina.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.content.entity.Tag;
import com.btchina.content.entity.TagQuestion;
import com.btchina.content.model.form.EditQuestionTagForm;
import com.btchina.content.manager.TagManager;
import com.btchina.content.mapper.TagQuestionMapper;
import com.btchina.content.model.form.AddTagForm;
import com.btchina.content.model.form.QueryQuestionTagForm;
import com.btchina.content.model.vo.TagVO;
import com.btchina.content.service.TagQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.content.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public Boolean deleteTag(Long questionId) {
        LambdaQueryWrapper<TagQuestion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TagQuestion::getQuestionId, questionId);
        remove(queryWrapper);
        return true;
    }

    @Override
    public List<TagVO> queryTag(QueryQuestionTagForm queryQuestionTagForm) {
        List<TagVO> tagVOS = new ArrayList<>();
        List<Long> ids = queryQuestionTagForm.getIds();
        for (Long id : ids) {
            List<Tag> tags = new ArrayList<>();
            LambdaQueryWrapper<TagQuestion> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TagQuestion::getQuestionId, id);
            List<TagQuestion> tagQuestions = list(queryWrapper);
            for (TagQuestion tagQuestion : tagQuestions) {
                Tag tag = tagService.getById(tagQuestion.getTagId());
                tags.add(tag);
            }
            TagVO tagVO = new TagVO();
            tagVO.setId(id);
            tagVO.setTags(tags);
            tagVOS.add(tagVO);
        }
        return tagVOS;
    }

    @Override
    public Boolean editTag(EditQuestionTagForm editQuestionTagForm) {
        Long id = editQuestionTagForm.getId();
        List<String> tags = editQuestionTagForm.getTags();
        // 1. 删除原有标签
        deleteTag(id);
        // 2. 添加新标签
        AddTagForm addTagForm = new AddTagForm();
        addTagForm.setId(id);
        addTagForm.setTags(tags);
        addTag(addTagForm);
        return true;

    }
}
