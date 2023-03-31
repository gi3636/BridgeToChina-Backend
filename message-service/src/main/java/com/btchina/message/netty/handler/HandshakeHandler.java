package com.btchina.message.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HandshakeHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("HandshakeHandler write");
        log.info("msg: {}", msg);
        // 如果不是握手消息，则按原样发送
        ctx.write(msg, promise);
    }

}
