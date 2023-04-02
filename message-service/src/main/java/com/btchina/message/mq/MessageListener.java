package com.btchina.message.mq;


import com.btchina.message.constant.MessageConstant;
import com.btchina.message.entity.Message;
import com.btchina.message.model.send.ChatMessage;
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
public class MessageListener {
    @Autowired
    MessageService messageService;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = MessageConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = MessageConstant.INSERT_QUEUE_NAME),
            key = {MessageConstant.INSERT_KEY}
    ))
    public void listenMessageInsert(ChatMessage message) {
        log.info("监听到新增消息，聊天消息为：{}", message);
        if (message != null) {
            messageService.add(message);
        }
    }


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = MessageConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = MessageConstant.DELETE_QUEUE_NAME),
            key = MessageConstant.DELETE_KEY
    ))
    public void listenMessageDelete(Long id) {
        log.info("监听到删除消息，聊天消息id为：{}", id);
        //messageService.deleteEsDoc(id);
        messageService.removeById(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = MessageConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = MessageConstant.UPDATE_QUEUE_NAME),
            key = MessageConstant.UPDATE_KEY
    ))
    public void listenMessageUpdate(Message message) {
        log.info("监听到更新消息，聊天消息为：{}", message);
        if (message != null) {
            messageService.updateById(message);
        }
    }

}
