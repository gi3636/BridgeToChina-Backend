package com.btchina.message.mq;


import com.btchina.message.constant.DialogConstant;
import com.btchina.message.constant.MessageConstant;
import com.btchina.message.entity.Message;
import com.btchina.message.model.send.ChatMessage;
import com.btchina.message.service.DialogUserService;
import com.btchina.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DialogListener {

    @Autowired
    DialogUserService dialogUserService;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = DialogConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = DialogConstant.REDUCE_UNREAD_COUNT_QUEUE_NAME),
            key = {DialogConstant.REDUCE_UNREAD_COUNT_KEY}
    ))
    public void listenDialogReduceUnreadCount(Message message) {
        log.info("监听到减少未读数消息，聊天消息为：{}", message);
        if (message != null) {
            dialogUserService.reduceUnreadCount(message.getDialogId(), message.getReceiverId());
        }
    }



}
