package com.btchina.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.entity.Question;
import com.btchina.feign.clients.MessageClient;
import com.btchina.feign.clients.QuestionClient;
import com.btchina.model.enums.ActionEnum;
import com.btchina.model.form.message.NotifyAddForm;
import com.btchina.model.vo.question.QuestionVO;
import com.btchina.user.entity.UserAction;
import com.btchina.user.mapper.UserActionMapper;
import com.btchina.model.form.user.UserActionForm;
import com.btchina.user.model.form.GetUserActionForm;
import com.btchina.user.model.vo.UserActionVO;
import com.btchina.user.model.vo.UserVO;
import com.btchina.user.service.UserActionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户动态表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@Service
@Slf4j
public class UserActionServiceImpl extends ServiceImpl<UserActionMapper, UserAction> implements UserActionService {

    @Autowired
    private MessageClient messageClient;

    @Autowired
    private QuestionClient questionClient;

    @Autowired
    private UserService userService;

    @Override
    public Boolean add(UserActionForm userActionForm) {
        UserAction userAction = new UserAction();
        BeanUtils.copyProperties(userActionForm, userAction);
        Boolean isSuccess = this.save(userAction);
        if (isSuccess) {
            //对象类型是问题
            NotifyAddForm notifyAddForm = new NotifyAddForm();
            switch (userActionForm.getObjectType()) {
                case 1:
                    //问题
                    Question question = questionClient.findById(userActionForm.getObjectId());
                    if (question != null) {
                        BeanUtils.copyProperties(userActionForm, notifyAddForm);
                        notifyAddForm.setSenderId(userActionForm.getUserId());
                        notifyAddForm.setReceiverId(question.getUserId());
                        notifyAddForm.setChannelType(1);
                        notifyAddForm.setTemplateId(1);
                    }
                    break;
                case 2:
                    //用户
                    BeanUtils.copyProperties(userActionForm, notifyAddForm);
                    notifyAddForm.setSenderId(userActionForm.getUserId());
                    notifyAddForm.setReceiverId(userActionForm.getObjectId());
                    notifyAddForm.setChannelType(1);
                    notifyAddForm.setTemplateId(1);
                    break;
                case 3:
                    //评论
                    break;
                default:
                    break;
            }
            messageClient.pushMessage(notifyAddForm);
        }
        return isSuccess;
    }

    @Override
    public Boolean delete(UserActionForm userActionForm) {
        LambdaQueryWrapper<UserAction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAction::getUserId, userActionForm.getUserId());
        queryWrapper.eq(UserAction::getActionType, userActionForm.getActionType());
        queryWrapper.eq(UserAction::getObjectId, userActionForm.getObjectId());
        queryWrapper.eq(UserAction::getObjectType, userActionForm.getObjectType());
        return this.remove(queryWrapper);
    }

    @Override
    public List<UserActionVO> list(GetUserActionForm getUserActionForm) {
        LambdaQueryWrapper<UserAction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAction::getUserId, getUserActionForm.getUserId());
        Page<UserAction> page = new Page<>(getUserActionForm.getCurrentPage(), getUserActionForm.getPageSize());
        Page<UserAction> userActionPage = this.page(page, queryWrapper);
        if (userActionPage.getRecords().size() > 0) {
            List<Long> userIds = new ArrayList<>();
            List<Long> questionIds = new ArrayList<>();
            userActionPage.getRecords().stream().forEach(userAction -> {
                if (userAction.getObjectType() == 1) {
                    questionIds.add(userAction.getObjectId());
                }else {
                    userIds.add(userAction.getObjectId());
                }
            });
            Map<Long, QuestionVO> questionVOMap = questionClient.findByIds(questionIds);
            Map<Long, UserVO> userVOMap = userService.findByIds(userIds);
            return userActionPage.getRecords().stream().map(userAction -> {
                UserActionVO userActionVO = new UserActionVO();
                BeanUtils.copyProperties(userAction, userActionVO);
                userActionVO.setActionName(ActionEnum.getActionEnum(userAction.getActionType()).getContent());
                if (userAction.getObjectType() == 1) {
                    userActionVO.setQuestion(questionVOMap.get(userAction.getObjectId()));
                }else {
                    userActionVO.setUser(userVOMap.get(userAction.getObjectId()));
                }
                return userActionVO;
            }).collect(Collectors.toList());

        }
        return new ArrayList<>();
    }
}
