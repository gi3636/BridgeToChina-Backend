package com.btchina.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.entity.Question;
import com.btchina.feign.clients.MessageClient;
import com.btchina.feign.clients.QuestionClient;
import com.btchina.model.form.message.NotifyAddForm;
import com.btchina.user.entity.UserAction;
import com.btchina.user.mapper.UserActionMapper;
import com.btchina.model.form.user.UserActionForm;
import com.btchina.user.service.UserActionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户动态表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@Service
public class UserActionServiceImpl extends ServiceImpl<UserActionMapper, UserAction> implements UserActionService {

    @Autowired
    private MessageClient messageClient;

    @Autowired
    private QuestionClient questionClient;

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
}
