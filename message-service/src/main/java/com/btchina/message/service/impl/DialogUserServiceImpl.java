package com.btchina.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.core.api.PageQueryParam;
import com.btchina.core.api.PageResult;
import com.btchina.entity.Answer;
import com.btchina.message.entity.DialogUser;
import com.btchina.message.mapper.DialogUserMapper;
import com.btchina.message.service.DialogUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户对话表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@Service
public class DialogUserServiceImpl extends ServiceImpl<DialogUserMapper, DialogUser> implements DialogUserService {
    @Override
    public Boolean add(Long userId, Long toUserId,Long dialogId) {
        DialogUser dialogUser = new DialogUser();
        dialogUser.setDialogId(dialogId);
        dialogUser.setUserId(userId);
        dialogUser.setToUserId(toUserId);
        return this.save(dialogUser);
    }

    @Override
    public List<DialogUser> getList(Long userId, PageQueryParam pageQueryParam) {
        LambdaQueryWrapper<DialogUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DialogUser::getUserId, userId);
        Page<DialogUser> page = new Page<>(pageQueryParam.getCurrentPage(), pageQueryParam.getPageSize());
        Page<DialogUser> dialogUserPage = baseMapper.selectPage(page, queryWrapper);
        return dialogUserPage.getRecords();
    }
}
