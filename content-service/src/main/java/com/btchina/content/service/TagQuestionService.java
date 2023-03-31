package com.btchina.content.service;

import com.btchina.content.entity.TagQuestion;
import com.btchina.content.model.form.AddTagForm;
import com.btchina.content.model.form.EditQuestionTagForm;
import com.btchina.content.model.form.QueryQuestionTagForm;
import com.btchina.content.model.vo.TagVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    List<TagVO> queryTag(QueryQuestionTagForm queryQuestionTagForm);

    Boolean editTag(EditQuestionTagForm editQuestionTagForm);
}
