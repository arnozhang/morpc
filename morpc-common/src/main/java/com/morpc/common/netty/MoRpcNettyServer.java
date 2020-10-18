package com.morpc.common.netty;

import com.morpc.common.netty.codec.MoRpcNettyDecoder;
import com.morpc.common.netty.codec.MoRpcNettyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * request server implemented by netty
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Setter
@Getter
@Slf4j
public class MoRpcNettyServer<T> {

    /**
     * server name
     */
    private String serverName;

    /**
     * request message handler
     */
    private MoRpcMessageHandler<T> messageHandler;

    /**
     * start request server
     *
     * @param host server host address
     * @param port server port
     */
    public void startServer(String host, int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap
            .group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel
                        .pipeline()
                        .addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 4, 0, 0))
                        .addLast(new MoRpcNettyDecoder())
                        .addLast(new MoRpcNettyEncoder())
                        .addLast(new SimpleChannelInboundHandler<T>() {

                            @Override
                            protected void channelRead0(
                                ChannelHandlerContext ctx, T message) throws Exception {

                                log.info("[{}] ** server channelRead0: {} - {}",
                                    serverName, message.getClass().getName(), message);

                                messageHandler.handleMessage(ctx, message);
                            }
                        });
                }
            })
            .option(ChannelOption.SO_BACKLOG, 128)
            .childOption(ChannelOption.SO_KEEPALIVE, true);

        // start registry server
        log.info("[{}] start server: {}:{}", serverName, host, port);

        // bind server
        ChannelFuture future = bootstrap.bind(host, port).sync();

        // start channel
        future.channel().closeFuture().sync();
    }
}
