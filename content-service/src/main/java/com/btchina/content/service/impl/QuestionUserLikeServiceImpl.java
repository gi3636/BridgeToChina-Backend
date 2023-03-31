package com.btchina.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.content.constant.QuestionConstant;
import com.btchina.content.entity.Question;
import com.btchina.content.entity.QuestionUserLike;
import com.btchina.content.mapper.QuestionUserLikeMapper;
import com.btchina.content.model.doc.QuestionDoc;
import com.btchina.content.service.QuestionService;
import com.btchina.content.service.QuestionUserLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 问题点赞表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-02-09
 */
@Service
public class QuestionUserLikeServiceImpl extends ServiceImpl<QuestionUserLikeMapper, QuestionUserLike> implements QuestionUserLikeService {


    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private QuestionService questionService;

    /**
     * 点赞
     *
     * @param questionId 问题id
     * @param userId     用户id
     * @return
     */
    @Override
    public Boolean like(Long questionId, Long userId) {
        QuestionDoc question = questionService.getEsDoc(questionId);
        if (question == null) {
            throw GlobalException.from(ResultCode.QUESTION_NOT_EXIST);
        }
        // 查询是否已经点赞
        QuestionUserLike questionUserLike = getByQuestionIdAndUserId(questionId, userId);
        // 曾经点赞过
        if (questionUserLike != null) {
            // 已经点赞
            if (questionUserLike.getStatus() == 1) {
                return true;
            }
            questionUserLike.setStatus(1);
            this.updateById(questionUserLike);
        } else {
            // 未点赞过
            QuestionUserLike userLike = new QuestionUserLike();
            userLike.setQuestionId(questionId);
            userLike.setUserId(userId);
            userLike.setStatus(1);
            this.save(userLike);
        }
        increaseLikeCount(question);
        return true;
    }


    @Override
    public Boolean unlike(Long questionId, Long userId) {
        QuestionDoc question = questionService.getEsDoc(questionId);
        if (question == null) {
            throw GlobalException.from(ResultCode.QUESTION_NOT_EXIST);
        }
        // 查询是否已经点赞
        QuestionUserLike questionUserLike = getByQuestionIdAndUserId(questionId, userId);
        // 曾经点赞过
        if (questionUserLike != null) {
            // 已经取消赞
            if (questionUserLike.getStatus() == 0) {
                return true;
            }
            questionUserLike.setStatus(0);
            this.updateById(questionUserLike);
        } else {
            // 未点赞过
            QuestionUserLike userUnlike = new QuestionUserLike();
            userUnlike.setQuestionId(questionId);
            userUnlike.setUserId(userId);
            userUnlike.setStatus(0);
            this.save(userUnlike);
        }
        decreaseLikeCount(question);
        return true;
    }

    @Override
    public QuestionUserLike getQuestionUserLike(Long questionId, Long userId) {
        if (questionId == null || userId == null) {
            return null;
        }
        return getByQuestionIdAndUserId(questionId, userId);
    }


    public QuestionUserLike getByQuestionIdAndUserId(Long questionId, Long userId) {
        LambdaQueryWrapper<QuestionUserLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QuestionUserLike::getQuestionId, questionId);
        queryWrapper.eq(QuestionUserLike::getUserId, userId);
        return this.getOne(queryWrapper);
    }

    /**
     * 增加点赞数
     *
     * @param questionDoc
     */
    public void increaseLikeCount(QuestionDoc questionDoc) {
        if (questionDoc == null) {
            throw GlobalException.from("问题不存在");
        }
        questionDoc.setLikeCount(questionDoc.getLikeCount() + 1);
        Question question = new Question();
        BeanUtils.copyProperties(questionDoc, question);
        Boolean isSuccess = questionService.updateById(question);
        if (isSuccess) {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.UPDATE_KEY, questionDoc);
        }
    }

    /**
     * 减少点赞数
     *
     * @param questionDoc
     */
    public void decreaseLikeCount(QuestionDoc questionDoc) {
        if (questionDoc == null) {
            throw GlobalException.from("问题不存在");
        }
        questionDoc.setLikeCount(questionDoc.getLikeCount() - 1);
        Question question = new Question();
        BeanUtils.copyProperties(questionDoc, question);
        Boolean isSuccess = questionService.updateById(question);
        if (isSuccess) {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.UPDATE_KEY, questionDoc);
        }
    }
}
