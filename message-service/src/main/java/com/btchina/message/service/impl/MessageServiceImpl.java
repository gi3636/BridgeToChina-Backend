package com.btchina.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.core.api.PageResult;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.message.constant.DialogConstant;
import com.btchina.message.constant.MessageConstant;
import com.btchina.message.entity.Message;
import com.btchina.message.mapper.MessageMapper;
import com.btchina.message.model.form.MessageQueryForm;
import com.btchina.message.model.send.ChatMessage;
import com.btchina.message.model.vo.MessageVO;
import com.btchina.message.service.DialogService;
import com.btchina.message.service.DialogUserService;
import com.btchina.message.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.feign.clients.UserClient;
import com.btchina.feign.model.user.vo.UserVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DialogService dialogService;

    @Autowired
    private DialogUserService dialogUserService;

    @Autowired
    private UserClient userClient;

    @Override
    public void signMessage(String msgId) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getMsgId, msgId);
        Message message = this.getOne(queryWrapper);
        if (message != null) {
            message.setSigned(true);
            rabbitTemplate.convertAndSend(MessageConstant.EXCHANGE_NAME, MessageConstant.UPDATE_KEY, message);
        }
    }

    @Override
    public PageResult<MessageVO> query(Long userId, MessageQueryForm messageQueryForm) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getDialogId, messageQueryForm.getDialogId());
        queryWrapper.orderByDesc(Message::getCreatedTime);
        Page<Message> page = new Page<>(messageQueryForm.getCurrentPage(), messageQueryForm.getPageSize());
        List<Message> messageList = this.page(page, queryWrapper).getRecords();
        //翻转顺序
        messageList = messageList.stream().sorted(Comparator.comparing(Message::getCreatedTime)).collect(Collectors.toList());
        List<MessageVO> messageVOList = new ArrayList<>();
        if (messageList != null && messageList.size() > 0) {
            //获取用户信息
            List<Long> userIds = new ArrayList<>();
            userIds.add(messageList.get(0).getSenderId());
            userIds.add(messageList.get(0).getReceiverId());
            Map<Long, UserVO> userVOMap = userClient.findByIds(userIds);

            //封装数据
            messageVOList = messageList.stream().map(message -> {
                MessageVO messageVO = new MessageVO();
                BeanUtils.copyProperties(message, messageVO);
                messageVO.setSenderNickname(userVOMap.get(message.getSenderId()).getNickname());
                messageVO.setSenderAvatar(userVOMap.get(message.getSenderId()).getAvatar());
                messageVO.setReceiverNickname(userVOMap.get(message.getReceiverId()).getNickname());
                messageVO.setReceiverAvatar(userVOMap.get(message.getReceiverId()).getAvatar());
                return messageVO;
            }).collect(Collectors.toList());
        }

        PageResult<MessageVO> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setList(messageVOList);
        pageResult.setPageSize(messageQueryForm.getPageSize());
        pageResult.setCurrentPage(messageQueryForm.getCurrentPage());
        pageResult.setTotalPage((int) page.getPages());
        return pageResult;
    }

    @Override
    public Boolean add(ChatMessage chatMessage) {
        Message message = new Message();
        BeanUtils.copyProperties(chatMessage, message);
        message.setIsRead(false);
        message.setSigned(false);
        message.setDialogId(chatMessage.getDialogId());
        //根据消息类型设置消息内容 例如图片消息，内容为 【图片】
        message.setContent(convertMessageContent(message));
        //调整对话未读消息数
        dialogUserService.addUnreadCount(chatMessage.getDialogId(), chatMessage.getReceiverId());
        //调整最后一条消息
        dialogService.updateLastMessage(chatMessage.getDialogId(), chatMessage.getMsgId(), message.getContent(),message.getMessageType());
        return this.save(message);
    }

    @Override
    public Boolean read(Long userId, String msgId) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getMsgId, msgId);
        Message message = this.getOne(queryWrapper);
        if (message == null) {
            throw GlobalException.from(ResultCode.MESSAGE_NOT_EXIST);
        }
        if (!message.getReceiverId().equals(userId)) {
            throw GlobalException.from(ResultCode.FORBIDDEN);
        }
        //如果消息已读，直接返回
        if (message.getIsRead()) {
            return true;
        }
        message.setIsRead(true);
        //更新对话未读消息数
        rabbitTemplate.convertAndSend(DialogConstant.EXCHANGE_NAME, DialogConstant.REDUCE_UNREAD_COUNT_KEY, message);
        return this.updateById(message);
    }

    public String convertMessageContent(Message message) {
        Integer messageType = message.getMessageType();
        switch (messageType) {
            case 2:
                return "【图片】";
        }
        return message.getContent();
    }


}
