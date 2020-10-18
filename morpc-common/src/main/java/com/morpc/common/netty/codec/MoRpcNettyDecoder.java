package com.morpc.common.netty.codec;

import com.morpc.common.serialize.MoSerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * netty decoder. byteArray -> object
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class MoRpcNettyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(
        ChannelHandlerContext channelHandlerContext,
        ByteBuf input, List<Object> outputs) throws Exception {

        if (input.readableBytes() < 4) {
            return;
        }

        input.markReaderIndex();
        int length = input.readInt();
        if (input.readableBytes() < length) {
            input.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        input.readBytes(bytes);

        Object object = MoSerializerFactory.getSerializer().deSerialize(bytes);
        outputs.add(object);
    }
}
