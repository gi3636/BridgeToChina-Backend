package com.btchina.content.tag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.content.tag.model.Tag;
import com.btchina.content.tag.model.TagQuestion;
import com.btchina.content.tag.manager.TagManager;
import com.btchina.content.tag.mapper.TagQuestionMapper;
import com.btchina.feign.model.tag.qo.TagAddQO;
import com.btchina.feign.model.tag.vo.TagListVO;
import com.btchina.feign.model.tag.vo.TagVO;
import com.btchina.feign.model.tag.qo.QueryQuestionTagQO;
import com.btchina.feign.model.tag.qo.QuestionEditTagQO;
import com.btchina.content.tag.service.TagQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.content.tag.service.TagService;
import org.springframework.beans.BeanUtils;
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
    public Boolean addTag(TagAddQO tagAddQO) {
        List<String> tags = tagAddQO.getTags();
        Long questionId = tagAddQO.getId();
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
    public List<TagListVO> queryTag(QueryQuestionTagQO queryQuestionTagQO) {
        List<TagListVO> tagVOS = new ArrayList<>();
        List<Long> ids = queryQuestionTagQO.getIds();
        for (Long id : ids) {
            List<TagVO> tags = new ArrayList<>();
            LambdaQueryWrapper<TagQuestion> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TagQuestion::getQuestionId, id);
            List<TagQuestion> tagQuestions = list(queryWrapper);
            for (TagQuestion tagQuestion : tagQuestions) {
                Tag tag = tagService.getById(tagQuestion.getTagId());
                TagVO tagVO = new TagVO();
                BeanUtils.copyProperties(tag, tagVO);
                tags.add(tagVO);
            }

            TagListVO tagListVO = new TagListVO();
            tagListVO.setId(id);
            tagListVO.setTags(tags);
            tagVOS.add(tagListVO);
        }
        return tagVOS;
    }

    @Override
    public Boolean editTag(QuestionEditTagQO editQuestionTagForm) {
        Long id = editQuestionTagForm.getId();
        List<String> tags = editQuestionTagForm.getTags();
        // 1. 删除原有标签
        deleteTag(id);
        // 2. 添加新标签
        TagAddQO tagAddQO = new TagAddQO();
        tagAddQO.setId(id);
        tagAddQO.setTags(tags);
        addTag(tagAddQO);
        return true;

    }
}
