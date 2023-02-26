package com.btchina.answer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.answer.entity.Answer;
import com.btchina.answer.entity.AnswerUserUse;
import com.btchina.answer.mapper.AnswerMapper;
import com.btchina.answer.model.form.AnswerUseForm;
import com.btchina.answer.service.AnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import com.btchina.core.exception.GlobalException;
import com.btchina.feign.clients.UserClient;
import com.btchina.model.form.answer.AddAnswerForm;
import com.btchina.model.form.answer.QueryAnswerForm;
import com.btchina.model.form.answer.UpdateAnswerForm;
import com.btchina.model.vo.answer.AnswerVO;
import com.btchina.model.vo.user.UserVO;
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
    private AnswerUserUseServiceImpl answerUserUseService;

    @Override
    public Boolean addAnswer(AddAnswerForm addAnswerForm, Long userId) {
        if (userId == null) {
            throw GlobalException.from("用户未登录");
        }
        Answer answer = new Answer();
        answer.setContent(addAnswerForm.getContent());
        answer.setUserId(userId);
        answer.setQuestionId(addAnswerForm.getQuestionId());
        answer.setUseCount(0);
        answer.setCommentCount(0);
        answer.setIsBest(0);
        return this.save(answer);
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

        return this.removeById(deleteForm.getId());
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
            answerVO.setNickName(user.getNickname());
            answerVO.setAvatar(user.getAvatar());
            answerVOList.add(answerVO);
        }

        // 封装返回结果
        PageResult<AnswerVO> pageResult = new PageResult<>();
        pageResult.setTotal(answerPage.getTotal());
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
