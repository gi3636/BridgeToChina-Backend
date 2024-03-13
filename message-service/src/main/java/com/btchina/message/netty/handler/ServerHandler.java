package com.btchina.message.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

/**
 * 定义worker端的处理器
 */
@Component
public class ServerHandler extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化通道以及配置对应管道的处理器
     *
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取管道（pipeline）
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 流水线管理通道中的处理程序（Handler），用来处理业务
        //websocket 基于http协议，所需要的http 编解码器
        pipeline.addLast(new HttpServerCodec());
        //在http上有一些数据流产生，有大有小，我们对其进行处理，既然如此，我们需要使用netty 对下数据流写 提供支持，这个类叫：ChunkedWriteHandler
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage 进行聚合处理，聚合成request或 response
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        /**
         * 针对客户端，如果在1分钟时间内没有向服务端发送读写心跳（ALL），则主动断开连接
         * 如果有读空闲和写空闲，则不做任何处理
         */
        pipeline.addLast(new IdleStateHandler(8, 10, 60));
        //自定义的空闲状态检测的handler
        pipeline.addLast(new HeartBeatHandler());
        /**
         * 本handler 会帮你处理一些繁重复杂的事情
         * 会帮你处理握手动作：handshaking（close、ping、pong） ping+pong = 心跳
         * 对于websocket 来讲，都是以frams 进行传输的，不同的数据类型对应的frams 也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //以上的handler都是固定的写法
        //自定义的handler,处理业务逻辑
        pipeline.addLast(new ServerListenerHandler());

    }
}

