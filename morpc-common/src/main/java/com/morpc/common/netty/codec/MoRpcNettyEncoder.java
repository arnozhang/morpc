package com.morpc.common.netty.codec;

import com.morpc.common.serialize.MoSerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * netty object encoder. object -> byteArray
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class MoRpcNettyEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(
        ChannelHandlerContext channelHandlerContext,
        Object object, ByteBuf byteBuf) throws Exception {

        byte[] bytes = MoSerializerFactory.getSerializer().serialize(object);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
