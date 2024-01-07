package com.btchina.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.core.api.PageQueryParam;
import com.btchina.core.api.PageResult;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.message.entity.Dialog;
import com.btchina.message.entity.DialogUser;
import com.btchina.message.mapper.DialogMapper;
import com.btchina.message.model.form.DialogAddForm;
import com.btchina.message.model.vo.DialogVO;
import com.btchina.message.service.DialogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.message.service.DialogUserService;
import com.btchina.feign.clients.UserClient;
import com.btchina.feign.model.user.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 会话详情表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@Service
public class DialogServiceImpl extends ServiceImpl<DialogMapper, Dialog> implements DialogService {
    @Autowired
    private DialogUserService dialogUserService;

    @Autowired
    private UserClient userClient;

    @Override
    public DialogVO add(Long userId, DialogAddForm dialogAddForm) {
        if (userId == null) {
            throw GlobalException.from(ResultCode.USER_NOT_LOGIN);
        }
        //判断是否已经存在会话
        LambdaQueryWrapper<DialogUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DialogUser::getUserId, userId);
        queryWrapper.eq(DialogUser::getToUserId, dialogAddForm.getToUserId());
        DialogUser dialogUser = dialogUserService.getOne(queryWrapper);
        UserVO toUser = userClient.findById(dialogAddForm.getToUserId());
        if (dialogUser != null) {
            DialogVO dialogVO = new DialogVO();
            Dialog dialog = this.baseMapper.selectById(dialogUser.getDialogId());
            BeanUtils.copyProperties(dialog, dialogVO);
            BeanUtils.copyProperties(dialogUser, dialogVO);
            dialogVO.setToUserAvatar(toUser.getAvatar());
            dialogVO.setToUserNickname(toUser.getNickname());
            dialogVO.setToUserId(toUser.getId());
            return dialogVO;
        }
        Dialog dialog = new Dialog();
        dialog.setLastMsgId("0");
        dialog.setChatType(1);
        dialog.setMessageType(0);
        dialog.setContent("");
        Boolean isSuccess = this.save(dialog);
        //绑定两个会话关系 一个是自己的 一个是对方的
        if (isSuccess) {
            dialogUserService.add(userId, dialogAddForm.getToUserId(), dialog.getId());
            dialogUserService.add(dialogAddForm.getToUserId(), userId, dialog.getId());
        }
        DialogUser dialogUser1 = dialogUserService.getOne(queryWrapper);
        DialogVO dialogVO = new DialogVO();
        BeanUtils.copyProperties(dialog, dialogVO);
        BeanUtils.copyProperties(dialogUser1, dialogVO);
        dialogVO.setToUserAvatar(toUser.getAvatar());
        dialogVO.setToUserNickname(toUser.getNickname());
        dialogVO.setToUserId(toUser.getId());
        return dialogVO;
    }

    @Override
    public PageResult<DialogVO> getList(Long userId, PageQueryParam pageQueryParam) {
        Page<DialogUser> dialogUserPage = dialogUserService.getList(userId, pageQueryParam);
        List<DialogUser> dialogUsers = dialogUserPage.getRecords();
        List<DialogVO> dialogVOList = new ArrayList<>();
        if (dialogUsers != null && dialogUsers.size() > 0) {
            //查询会话信息
            List<Long> dialogIds = dialogUsers.stream().map(DialogUser::getDialogId).collect(Collectors.toList());
            Map<Long, Dialog> dialogMap = this.baseMapper.selectBatchIds(dialogIds).stream().collect(Collectors.toMap(Dialog::getId, dialog -> dialog));
            //查询用户信息
            List<Long> toUserIds = dialogUsers.stream().map(DialogUser::getToUserId).collect(Collectors.toList());
            Map<Long, UserVO> userVOMap = userClient.findByIds(toUserIds);
            //封装数据
            for (DialogUser dialogUser : dialogUsers) {
                DialogVO dialogVO = new DialogVO();
                BeanUtils.copyProperties(dialogMap.get(dialogUser.getDialogId()), dialogVO);
                BeanUtils.copyProperties(dialogUser, dialogVO);
                dialogVO.setToUserId(dialogUser.getToUserId());
                dialogVO.setToUserAvatar(userVOMap.get(dialogUser.getToUserId()).getAvatar());
                dialogVO.setToUserNickname(userVOMap.get(dialogUser.getToUserId()).getNickname());
                dialogVOList.add(dialogVO);
            }
        }

        PageResult<DialogVO> pageResult = new PageResult<>();
        pageResult.setList(dialogVOList);
        pageResult.setTotal(dialogUserPage.getTotal());
        pageResult.setPageSize(pageQueryParam.getPageSize());
        pageResult.setCurrentPage(pageQueryParam.getCurrentPage());
        pageResult.setTotalPage((int) dialogUserPage.getPages());
        return pageResult;
    }

    @Override
    public Boolean updateLastMessage(Long dialogId, String msgId, String content, Integer messageType) {
        LambdaQueryWrapper<Dialog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dialog::getId, dialogId);
        Dialog dialog = this.getOne(queryWrapper);
        if (dialog != null) {
            dialog.setLastMsgId(msgId);
            dialog.setContent(content);
            dialog.setMessageType(messageType);
            return this.updateById(dialog);
        }
        return false;
    }
}
