package com.btchina.content.service;

import com.btchina.content.entity.QuestionUserFavorite;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.content.model.form.QuestionFavouriteForm;

/**
 * <p>
 * 问题收藏表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-02-09
 */
public interface QuestionUserFavoriteService extends IService<QuestionUserFavorite> {

    Boolean favourite(QuestionFavouriteForm questionFavouriteForm, Long userId);

    QuestionUserFavorite getQuestionUserFavorite(Long id, Long selfId);
}
