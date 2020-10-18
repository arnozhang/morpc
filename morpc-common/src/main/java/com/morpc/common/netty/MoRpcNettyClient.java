package com.morpc.common.netty;

import com.morpc.common.netty.codec.MoRpcNettyDecoder;
import com.morpc.common.netty.codec.MoRpcNettyEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * request client implemented by netty
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
@Slf4j
public class MoRpcNettyClient<T> {

    /**
     * client name
     */
    private String clientName;

    /**
     * request message handler
     */
    private MoRpcMessageHandler<T> messageHandler;

    /**
     * request channel
     */
    private Channel requestChannel;

    /**
     * request event loop0 group
     */
    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);

    /**
     * start client and connect remote server
     *
     * @param host remote server host
     * @param port remote server port
     */
    public void startClient(String host, int port) throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
            .group(eventLoopGroup)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel
                        .pipeline()
                        .addLast(new MoRpcNettyDecoder())
                        .addLast(new MoRpcNettyEncoder())
                        .addLast(new SimpleChannelInboundHandler<T>() {

                            @Override
                            protected void channelRead0(
                                ChannelHandlerContext ctx, T message) throws Exception {

                                log.info("[{}] ** client channelRead0: {} - {}",
                                    clientName, message.getClass().getName(), message);

                                messageHandler.handleMessage(ctx, message);
                            }
                        });
                }
            });

        log.info("[{}] will connect: {}:{}", clientName, host, port);

        // connect remote service
        ChannelFuture future = bootstrap.connect(host, port).sync();
        future.addListener(listener -> {
            if (!future.isSuccess()) {
                log.error(String.format("[%s] client channel error", clientName), future.cause());

                // close
                eventLoopGroup.shutdownGracefully();
            }
        });

        // send rpc request
        requestChannel = future.channel();
    }

    /**
     * stop client
     */
    public void stopClient() {
        log.info("[{}] stop MoRegistryCenterClient", clientName);

        if (requestChannel != null) {
            requestChannel.closeFuture();
        }

        eventLoopGroup.shutdownGracefully();
    }

    /**
     * send request message
     *
     * @param msg request object
     */
    public void sendMessage(Object msg) throws Exception {
        requestChannel.writeAndFlush(msg).sync();
    }
}
