package com.btchina.content.notification.mq;


import com.btchina.content.notification.model.vo.NotifyVO;
import com.btchina.content.notification.model.Notify;
import com.btchina.core.util.JsonUtils;
import com.btchina.message.constant.NotifyConstant;
import com.btchina.message.model.send.NotificationMessage;
import com.btchina.message.netty.UserConnectPool;
import com.btchina.content.notification.service.NotifyService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class NotifyListener {

    @Autowired
    private NotifyService notifyService;

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
                List<Notify> notifyList = new ArrayList<>();
                notifyList.add(notify);
                List<NotifyVO> notifyVOS =  notifyService.convertToNotifyVOList(notifyList);
                //notificationMessage.setNotifyVO(notifyVOS.get(0)); // TODO 修改类型
                // 用户在线
                receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(notificationMessage)));
            }
        }
    }


}
