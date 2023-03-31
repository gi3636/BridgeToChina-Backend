package com.btchina.message.netty.handler;


import com.btchina.core.util.JsonUtils;
import com.btchina.message.enums.MessageActionEnum;
import com.btchina.message.netty.UserConnectPool;
import com.btchina.message.netty.bean.ChatMsg;
import com.btchina.message.netty.bean.DataContent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Slf4j
@Component
@ChannelHandler.Sharable
public class ServerListenerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 当建立链接时将Channel放置在Group当中
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("有新的客户端链接：[{}]", ctx.channel().id().asLongText());
        // 添加到channelGroup 通道组
        UserConnectPool.getChannelGroup().add(ctx.channel());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("连接成功111\n"));
    }

    /**
     * 读取数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        /**
         * 1.接受到msg
         * 2.将msg转化为实体类
         * 3.解析消息类型
         * 将实体类当中的userid和连接的Channel进行对应
         * */
        String content = msg.text();
        Channel channel = ctx.channel();
        System.out.println("接收到消息: " + content);
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        assert dataContent != null;
        MessageActionEnum action = MessageActionEnum.getMessageActionEnum(dataContent.getAction());
        // 判断消息类型，根据不同的类型来处理不同的业务
        switch (action) {
            case CONNECT:
                // 1.第一次(或重连)初始化连接
                String senderId = dataContent.getChatMsg().getSenderId();
                UserConnectPool.getChannelMap().put(senderId, channel);
                // 将用户ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
                AttributeKey<String> key = AttributeKey.valueOf("userId");
                ctx.channel().attr(key).setIfAbsent(senderId);
                break;
            case CHAT:
                // 2.聊天消息，把聊天记录保存到数据库，同时标记消息的签收状态[未签收]
                ChatMsg chatMsg = dataContent.getChatMsg();
                String receiverId = chatMsg.getReceiverId();
                Channel receiverChannel = UserConnectPool.getChannelMap().get(receiverId);
                if (Objects.nonNull(receiverChannel)) {
                    // 当receiverChannel不为空的时候，从ChannelGroup当中查找对应的channel是否存在
                    Channel findChannel = UserConnectPool.getChannelGroup().find(receiverChannel.id());
                    if (Objects.nonNull(findChannel)) {
                        // 用户在线
                        receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContent)));
                        // TODO 保存消息到数据库，并且标记为 未签收

                    } else {
                        // 用户离线 TODO 推送消息
                    }
                } else {
                    // 用户离线 TODO 推送消息
                }
                break;

            case SIGNED:
                // 3.签收消息，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收]
                ChatMsg signedMsg = dataContent.getChatMsg();
                String signedMsgId = signedMsg.getMsgId();
                // TODO 签收消息，修改数据库中对应消息的签收状态[已签收]
                break;

            case KEEPALIVE:
                // 4.心跳类型的消息
                log.info("收到来自channel为[{}]的心跳包...", ctx.channel().id());
                break;

            case PULL_FRIEND:
                // 5.拉取好友

                break;

        }

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("用户下线了:{}", ctx.channel().id().asLongText());
        // 删除通道
        UserConnectPool.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常
        log.info("异常：{}", cause.getMessage());
        // 删除通道
        UserConnectPool.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
        ctx.close();
    }

    /**
     * 删除用户与channel的对应关系
     */
    private void removeUserId(ChannelHandlerContext ctx) {
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        String userId = ctx.channel().attr(key).get();
        UserConnectPool.getChannelMap().remove(userId);
    }
}

