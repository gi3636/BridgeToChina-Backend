package com.btchina.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.content.constant.QuestionConstant;
import com.btchina.content.entity.QuestionUserFavorite;
import com.btchina.content.mapper.QuestionUserFavoriteMapper;
import com.btchina.content.model.form.QuestionFavouriteForm;
import com.btchina.content.service.QuestionService;
import com.btchina.content.service.QuestionUserFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 问题收藏表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-02-09
 */
@Service
public class QuestionUserFavoriteServiceImpl extends ServiceImpl<QuestionUserFavoriteMapper, QuestionUserFavorite> implements QuestionUserFavoriteService {


    @Autowired
    private QuestionService questionService;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 收藏问题
     *
     * @param questionFavouriteForm
     * @param userId
     * @return
     */
    @Override
    public Boolean favourite(QuestionFavouriteForm questionFavouriteForm, Long userId) {
        if (userId == null) {
            throw GlobalException.from(ResultCode.UNAUTHORIZED);
        }
        if (questionService.getById(questionFavouriteForm.getQuestionId()) == null) {
            throw GlobalException.from(ResultCode.QUESTION_NOT_EXIST);
        }
        QuestionUserFavorite questionUserFavorite = new QuestionUserFavorite();
        LambdaQueryWrapper<QuestionUserFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QuestionUserFavorite::getQuestionId, questionFavouriteForm.getQuestionId());
        queryWrapper.eq(QuestionUserFavorite::getUserId, userId);
        questionUserFavorite = this.baseMapper.selectOne(queryWrapper);
        // 1. 判断是否已经收藏
        if (questionUserFavorite == null) {
            questionUserFavorite = new QuestionUserFavorite();
            questionUserFavorite.setStatus(1);
            questionUserFavorite.setQuestionId(questionFavouriteForm.getQuestionId());
            questionUserFavorite.setUserId(userId);
            this.baseMapper.insert(questionUserFavorite);
        } else {
            if (Objects.equals(questionUserFavorite.getStatus(), questionFavouriteForm.getStatus())) {
                return true;
            }
            questionUserFavorite.setStatus(questionFavouriteForm.getStatus());
            this.baseMapper.updateById(questionUserFavorite);
        }

        // 2. 增加计数
        if (questionFavouriteForm.getStatus() == 1) {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.INCREASE_FAVOURITE_COUNT_ROUTING_KEY, questionFavouriteForm.getQuestionId());
        } else {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.DECREASE_FAVOURITE_COUNT_ROUTING_KEY, questionFavouriteForm.getQuestionId());
        }
        return true;
    }

    @Override
    public QuestionUserFavorite getQuestionUserFavorite(Long id, Long selfId) {
        LambdaQueryWrapper<QuestionUserFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QuestionUserFavorite::getQuestionId, id);
        queryWrapper.eq(QuestionUserFavorite::getUserId, selfId);
        return this.baseMapper.selectOne(queryWrapper);
    }
}
