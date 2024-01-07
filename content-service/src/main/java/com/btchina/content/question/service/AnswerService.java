package com.btchina.content.question.service;

import com.btchina.content.question.model.Answer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import com.btchina.content.question.model.qo.AnswerAddQO;
import com.btchina.content.question.model.qo.AnswerQueryQO;
import com.btchina.content.question.model.qo.AnswerUpdateQO;
import com.btchina.feign.model.question.vo.AnswerVO;

/**
 * <p>
 * 回答表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
public interface AnswerService extends IService<Answer> {

    Boolean addAnswer(AnswerAddQO answerAddQO, Long userId);

    Boolean delAnswer(DeleteForm deleteForm, Long userId);

    Boolean updateAnswer(AnswerUpdateQO answerUpdateQO, Long userId);

    PageResult<AnswerVO> queryAnswer(AnswerQueryQO answerQueryQO, Long userId);

    Boolean use(Long id, Integer status, Long userId);


    AnswerVO findVOById(Long answerId);

    void increaseCommentCount(Long answerId);

    void decreaseCommentCount(Long answerId);

    Answer findById(Long answerId);
}
