package com.btchina.content.question.service;

import com.btchina.content.question.model.QuestionUserLike;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 问题点赞表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-02-09
 */
public interface QuestionUserLikeService extends IService<QuestionUserLike> {

    Boolean like(Long questionId, Long selfId);

    Boolean unlike(Long questionId, Long userId);

    QuestionUserLike getQuestionUserLike(Long questionId, Long userId);
}
