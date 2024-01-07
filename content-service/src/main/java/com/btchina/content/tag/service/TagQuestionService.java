package com.btchina.content.tag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.content.tag.model.TagQuestion;
import com.btchina.content.tag.feign.qo.TagAddQO;
import com.btchina.content.tag.feign.vo.TagVO;
import com.btchina.content.question.feign.qo.QueryQuestionTagQO;
import com.btchina.content.question.feign.qo.QuestionEditTagQO;

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

    Boolean addTag(TagAddQO tagAddQO);

    Boolean deleteTag(Long questionId);

    List<TagVO> queryTag(QueryQuestionTagQO queryQuestionTagQO);


    Boolean editTag(QuestionEditTagQO editQuestionTagForm);
}
