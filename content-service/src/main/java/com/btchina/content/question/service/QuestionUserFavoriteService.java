package com.btchina.content.question.service;

import com.btchina.content.question.feign.qo.QuestionFavouriteQO;
import com.btchina.content.question.model.QuestionUserFavorite;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 问题收藏表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-02-09
 */
public interface QuestionUserFavoriteService extends IService<QuestionUserFavorite> {

    Boolean favourite(QuestionFavouriteQO questionFavouriteQO, Long userId);

    QuestionUserFavorite getQuestionUserFavorite(Long id, Long selfId);
}
