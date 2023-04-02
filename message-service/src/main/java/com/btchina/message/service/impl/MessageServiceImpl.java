package com.btchina.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.feign.clients.UserClient;
import com.btchina.message.constant.MessageConstant;
import com.btchina.message.entity.Message;
import com.btchina.message.mapper.MessageMapper;
import com.btchina.message.model.form.MessageQueryForm;
import com.btchina.message.model.send.ChatMessage;
import com.btchina.message.model.vo.MessageVO;
import com.btchina.message.service.DialogService;
import com.btchina.message.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public List<MessageVO> query(Long userId, MessageQueryForm messageQueryForm) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getDialogId, messageQueryForm.getDialogId());
        Page<Message> page = new Page<>(messageQueryForm.getCurrentPage(), messageQueryForm.getPageSize());
        List<Message> messageList = this.page(page, queryWrapper).getRecords();
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
        return messageVOList;
    }

    @Override
    public Boolean add(ChatMessage chatMessage) {
        Message message = new Message();
        BeanUtils.copyProperties(chatMessage, message);
        //TODO 调整对话未读消息数
        //TODO 调整最后一条消息
        //TODO 根据消息类型设置消息内容 例如图片消息，内容为 【图片】
        return this.save(message);
    }


}
