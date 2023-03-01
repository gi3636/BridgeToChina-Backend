package com.btchina.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.question.constant.AnswerConstant;
import com.btchina.question.entity.Answer;
import com.btchina.question.entity.AnswerUserUse;
import com.btchina.question.mapper.AnswerMapper;
import com.btchina.question.service.AnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import com.btchina.core.exception.GlobalException;
import com.btchina.entity.Question;
import com.btchina.entity.User;
import com.btchina.feign.clients.QuestionClient;
import com.btchina.feign.clients.UserClient;
import com.btchina.question.model.form.AddAnswerForm;
import com.btchina.question.model.form.QueryAnswerForm;
import com.btchina.question.model.form.UpdateAnswerForm;
import com.btchina.model.vo.answer.AnswerVO;
import com.btchina.model.vo.user.UserVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 回答表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private QuestionClient questionClient;

    @Autowired
    private AnswerUserUseServiceImpl answerUserUseService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public Boolean addAnswer(AddAnswerForm addAnswerForm, Long userId) {
        if (userId == null) {
            throw GlobalException.from("用户未登录");
        }
        Question question = questionClient.findById(addAnswerForm.getQuestionId());
        if (question == null) {
            throw GlobalException.from("问题不存在");
        }
        Answer answer = new Answer();
        answer.setContent(addAnswerForm.getContent());
        answer.setUserId(userId);
        answer.setQuestionId(addAnswerForm.getQuestionId());
        answer.setUseCount(0);
        answer.setCommentCount(0);
        answer.setIsBest(0);
        Boolean isSuccess = this.save(answer);
        if (isSuccess) {
            // 增加问题回答数
            rabbitTemplate.convertAndSend(AnswerConstant.EXCHANGE_NAME, AnswerConstant.INCREASE_ROUTING_KEY, addAnswerForm.getQuestionId());
        }
        return isSuccess;
    }

    @Override
    public Boolean delAnswer(DeleteForm deleteForm, Long userId) {
        if (userId == null) {
            throw GlobalException.from("用户未登录");
        }
        Answer answer = this.getById(deleteForm.getId());

        if (answer == null) {
            throw GlobalException.from("回答不存在");
        }

        if (!answer.getUserId().equals(userId)) {
            throw GlobalException.from("无权删除");
        }

        Boolean isSuccess = this.removeById(deleteForm.getId());
        if (isSuccess) {
            // 增加问题回答数
            rabbitTemplate.convertAndSend(AnswerConstant.EXCHANGE_NAME, AnswerConstant.DECREASE_ROUTING_KEY, answer.getQuestionId());
        }
        return isSuccess;
    }

    @Override
    public Boolean updateAnswer(UpdateAnswerForm updateAnswerForm, Long userId) {
        if (userId == null) {
            throw GlobalException.from("用户未登录");
        }
        Answer answer = this.getById(updateAnswerForm.getId());
        if (answer == null) {
            throw GlobalException.from("回答不存在");
        }
        if (!answer.getUserId().equals(userId)) {
            throw GlobalException.from("无权修改");
        }

        answer.setContent(updateAnswerForm.getContent());
        return this.updateById(answer);
    }

    @Override
    public PageResult<AnswerVO> queryAnswer(QueryAnswerForm queryAnswerForm) {
        // 封装查询条件
        LambdaQueryWrapper<Answer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Answer::getQuestionId, queryAnswerForm.getQuestionId());
        wrapper.orderByDesc(Answer::getUseCount);
        Page<Answer> page = new Page<>(queryAnswerForm.getCurrentPage(), queryAnswerForm.getPageSize());

        // 查询
        Page<Answer> answerPage = baseMapper.selectPage(page, wrapper);

        // 查询用户信息
        List<Answer> answers = answerPage.getRecords();
        List<Long> userIds = answers.stream().map(Answer::getUserId).collect(Collectors.toList());
        Map<Long, UserVO> userMap = userClient.findByIds(userIds);
        List<AnswerVO> answerVOList = new ArrayList<>();
        for (Answer answer : answers) {
            UserVO user = userMap.get(answer.getUserId());
            AnswerVO answerVO = new AnswerVO();
            BeanUtils.copyProperties(answer, answerVO);
            answerVO.setNickname(user.getNickname());
            answerVO.setAvatar(user.getAvatar());
            answerVOList.add(answerVO);
        }

        // 封装返回结果
        PageResult<AnswerVO> pageResult = new PageResult<>();
        pageResult.setTotal(answerPage.getTotal());
        pageResult.setTotalPage((int) answerPage.getPages());
        pageResult.setPageSize((int) answerPage.getSize());
        pageResult.setCurrentPage((int) answerPage.getCurrent());
        pageResult.setList(answerVOList);
        return pageResult;
    }

    @Override
    public Boolean use(Long id, Integer status, Long userId) {
        if (userId == null) {
            throw GlobalException.from("用户未登录");
        }
        Answer answer = this.getById(id);
        if (answer == null) {
            throw GlobalException.from("回答不存在");
        }
        // 先查询该用户是否已经采用过该回答
        LambdaQueryWrapper<AnswerUserUse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AnswerUserUse::getUserId, userId);
        wrapper.eq(AnswerUserUse::getAnswerId, answer.getId());
        AnswerUserUse answerUserUse = answerUserUseService.getOne(wrapper);
        if (answerUserUse == null) {
            // 如果没有采用过，则新增一条记录
            answerUserUse = new AnswerUserUse();
            answerUserUse.setUserId(userId);
            answerUserUse.setAnswerId(answer.getId());
            answerUserUse.setStatus(status);
            answerUserUseService.save(answerUserUse);
        } else {
            // 如果已经采用过，则判断状态
            if (answerUserUse.getStatus() == status) {
                return true;
            }
            // 如果已经采用过，则更新状态
            answerUserUse.setStatus(status);
            answerUserUseService.updateById(answerUserUse);
        }
        // 增加采用数
        if (answerUserUse.getStatus() == 1) {
            incUseCount(answer.getId());
        } else {
            decUseCount(answer.getId());
        }

        return true;
    }

    @Override
    public AnswerVO findVOById(Long answerId) {
        Answer answer = this.getById(answerId);
        if (answer == null) {
            throw GlobalException.from("回答不存在");
        }
        AnswerVO answerVO = new AnswerVO();
        BeanUtils.copyProperties(answer, answerVO);
        User user = userClient.findById(answer.getUserId());
        if (user != null) {
            answerVO.setNickname(user.getNickname());
            answerVO.setAvatar(user.getAvatar());
        }

        AnswerUserUse answerUserUse = answerUserUseService.getOne(new LambdaQueryWrapper<AnswerUserUse>()
                .eq(AnswerUserUse::getAnswerId, answerId)
                .eq(AnswerUserUse::getUserId, answer.getUserId()));
      if (answerUserUse != null) {
            answerVO.setUseStatus(answerUserUse.getStatus());
      }
        return answerVO;
    }

    @Override
    public void increaseCommentCount(Long answerId) {
        Answer answer = this.getById(answerId);
        answer.setCommentCount(answer.getCommentCount() + 1);
        this.updateById(answer);
    }

    @Override
    public void decreaseCommentCount(Long answerId) {
        Answer answer = this.getById(answerId);
        answer.setCommentCount(answer.getCommentCount() - 1);
        this.updateById(answer);
    }

    @Override
    public Answer findById(Long answerId) {
        return this.getById(answerId);
    }

    private Boolean decUseCount(Long id) {
        Answer answer = this.getById(id);
        answer.setUseCount(answer.getUseCount() - 1);
        return this.updateById(answer);
    }

    public Boolean incUseCount(Long answerId) {
        Answer answer = this.getById(answerId);
        answer.setUseCount(answer.getUseCount() + 1);
        return this.updateById(answer);
    }
}
