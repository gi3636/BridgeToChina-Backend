package com.btchina.content.tag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.content.tag.model.TagQuestion;
import com.btchina.feign.model.tag.qo.TagAddQO;
import com.btchina.feign.model.tag.vo.TagListVO;
import com.btchina.feign.model.tag.vo.TagVO;
import com.btchina.feign.model.tag.qo.QueryQuestionTagQO;
import com.btchina.feign.model.tag.qo.QuestionEditTagQO;

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

    List<TagListVO> queryTag(QueryQuestionTagQO queryQuestionTagQO);


    Boolean editTag(QuestionEditTagQO editQuestionTagForm);
}
