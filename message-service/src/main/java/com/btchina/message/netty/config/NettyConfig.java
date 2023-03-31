package com.btchina.message.netty.config;

import com.btchina.message.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class NettyConfig {
    @Autowired
    NettyProperties nettyProperties;

    /**
     * boss 线程池
     * 负责客户端连接
     *
     * @return
     */
    @Bean
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
    }

    /**
     * worker线程池
     * 负责业务处理
     *
     * @return
     */
    @Bean
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 20);
    }

    /**
     * 服务器启动器
     *
     * @return
     */
    @Bean
    public ServerBootstrap serverBootstrap() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup(), workerGroup())   // 指定使用的线程组
                .channel(NioServerSocketChannel.class) // 指定使用的通道
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, nettyProperties.getTimeout()) // 指定连接超时时间
                .childHandler(new ServerHandler()); // 指定worker处理器
        return serverBootstrap;
    }

}

