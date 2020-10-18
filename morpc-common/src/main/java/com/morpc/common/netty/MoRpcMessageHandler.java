package com.morpc.common.netty;

import io.netty.channel.ChannelHandlerContext;

/**
 * rpc message handler
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public interface MoRpcMessageHandler<T> {

    /**
     * handle received message
     */
    void handleMessage(ChannelHandlerContext ctx, T t) throws Exception;
}
