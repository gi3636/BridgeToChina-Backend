package com.btchina.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.core.api.PageQueryParam;
import com.btchina.feign.clients.UserClient;
import com.btchina.message.entity.Dialog;
import com.btchina.message.entity.DialogUser;
import com.btchina.message.mapper.DialogMapper;
import com.btchina.message.model.form.DialogAddForm;
import com.btchina.message.model.vo.DialogVO;
import com.btchina.message.service.DialogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.message.service.DialogUserService;
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
    public Boolean add(Long userId, DialogAddForm dialogAddForm) {
        Dialog dialog = new Dialog();
        Boolean isSuccess = this.save(dialog);
        //绑定两个会话关系 一个是自己的 一个是对方的
        if (isSuccess) {
            dialogUserService.add(userId, dialogAddForm.getToUserId(), dialog.getId());
            dialogUserService.add(dialogAddForm.getToUserId(), userId, dialog.getId());
        }
        return isSuccess;
    }

    @Override
    public List<DialogVO> getList(Long userId, PageQueryParam pageQueryParam) {
        List<DialogUser> dialogUsers = dialogUserService.getList(userId, pageQueryParam);
        List<DialogVO> dialogVOList = new ArrayList<>();
        if (dialogUsers != null && dialogUsers.size() > 0) {
            //获取回话
            List<Long> toUserIds = dialogUsers.stream().map(DialogUser::getToUserId).collect(Collectors.toList());
            //查询用户信息
            Map<Long, UserVO> userVOMap = userClient.findByIds(toUserIds);
            //封装数据
            for (DialogUser dialogUser : dialogUsers) {
                DialogVO dialogVO = new DialogVO();
                BeanUtils.copyProperties(dialogUser, dialogVO);
                dialogVO.setToUserId(dialogUser.getToUserId());
                dialogVO.setToUserAvatar(userVOMap.get(dialogUser.getToUserId()).getAvatar());
                dialogVO.setToUserNickname(userVOMap.get(dialogUser.getToUserId()).getNickname());
                dialogVOList.add(dialogVO);
            }
        }
        return dialogVOList;
    }

    @Override
    public Boolean updateLastMessage(String dialogId, String msgId, String content) {
        LambdaQueryWrapper<Dialog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dialog::getId, dialogId);
        Dialog dialog = this.getOne(queryWrapper);
        if (dialog != null) {
            dialog.setLastMsgId(msgId);
            dialog.setContent(content);
            return this.updateById(dialog);
        }
        return false;
    }
}
