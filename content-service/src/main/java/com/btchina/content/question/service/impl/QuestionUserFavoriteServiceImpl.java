package com.btchina.content.question.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.content.question.model.qo.QuestionFavouriteQO;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.content.infra.constant.QuestionConstant;
import com.btchina.content.question.model.QuestionUserFavorite;
import com.btchina.content.question.mapper.QuestionUserFavoriteMapper;

import com.btchina.content.question.service.QuestionService;
import com.btchina.content.question.service.QuestionUserFavoriteService;
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
     * @param questionFavouriteQO
     * @param userId
     * @return
     */
    @Override
    public Boolean favourite(QuestionFavouriteQO questionFavouriteQO, Long userId) {
        if (userId == null) {
            throw GlobalException.from(ResultCode.UNAUTHORIZED);
        }
        if (questionService.getById(questionFavouriteQO.getQuestionId()) == null) {
            throw GlobalException.from(ResultCode.QUESTION_NOT_EXIST);
        }
        QuestionUserFavorite questionUserFavorite = new QuestionUserFavorite();
        LambdaQueryWrapper<QuestionUserFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QuestionUserFavorite::getQuestionId, questionFavouriteQO.getQuestionId());
        queryWrapper.eq(QuestionUserFavorite::getUserId, userId);
        questionUserFavorite = this.baseMapper.selectOne(queryWrapper);
        // 1. 判断是否已经收藏
        if (questionUserFavorite == null) {
            questionUserFavorite = new QuestionUserFavorite();
            questionUserFavorite.setStatus(1);
            questionUserFavorite.setQuestionId(questionFavouriteQO.getQuestionId());
            questionUserFavorite.setUserId(userId);
            this.baseMapper.insert(questionUserFavorite);
        } else {
            if (Objects.equals(questionUserFavorite.getStatus(), questionFavouriteQO.getStatus())) {
                return true;
            }
            questionUserFavorite.setStatus(questionFavouriteQO.getStatus());
            this.baseMapper.updateById(questionUserFavorite);
        }

        // 2. 增加计数
        if (questionFavouriteQO.getStatus() == 1) {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.INCREASE_FAVOURITE_COUNT_ROUTING_KEY, questionFavouriteQO.getQuestionId());
        } else {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.DECREASE_FAVOURITE_COUNT_ROUTING_KEY, questionFavouriteQO.getQuestionId());
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
