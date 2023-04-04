package com.btchina.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.user.entity.UserAction;
import com.btchina.user.mapper.UserActionMapper;
import com.btchina.model.form.user.UserActionForm;
import com.btchina.user.service.UserActionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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

    @Override
    public Boolean add(UserActionForm userActionForm) {
        UserAction userAction = new UserAction();
        BeanUtils.copyProperties(userActionForm, userAction);
        return this.save(userAction);
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
