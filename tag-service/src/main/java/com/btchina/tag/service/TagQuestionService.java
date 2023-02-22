package com.btchina.tag.service;

import com.btchina.tag.entity.TagQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.tag.model.form.AddTagForm;

/**
 * <p>
 * 标签问题表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-02-06
 */
public interface TagQuestionService extends IService<TagQuestion> {

    Boolean addTag(AddTagForm addTagForm);

    Boolean deleteTag(Long questionId);
}
