package com.btchina.message.mq;


import com.btchina.core.util.JsonUtils;
import com.btchina.message.constant.NotifyConstant;
import com.btchina.message.entity.Notify;
import com.btchina.message.model.send.NotificationMessage;
import com.btchina.message.netty.UserConnectPool;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class NotifyListener {


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = NotifyConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = NotifyConstant.PUSH_QUEUE_NAME),
            key = {NotifyConstant.PUSH_KEY}
    ))
    public void listenPush(Notify notify) {
        log.info("监听到消息通知，消息为：{}", notify);
        Channel receiverChannel = UserConnectPool.getChannel(notify.getReceiverId().toString());
        if (Objects.nonNull(receiverChannel)) {
            // 当receiverChannel不为空的时候，从ChannelGroup当中查找对应的channel是否存在
            Channel findChannel = UserConnectPool.getChannelGroup().find(receiverChannel.id());
            if (Objects.nonNull(findChannel)) {
                NotificationMessage notificationMessage = new NotificationMessage();
                BeanUtils.copyProperties(notify, notificationMessage);
                // 用户在线
                receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(notificationMessage)));
            }
        }
    }


}
