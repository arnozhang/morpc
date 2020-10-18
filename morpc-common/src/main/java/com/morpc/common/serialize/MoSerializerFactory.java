package com.morpc.common.serialize;

import com.morpc.common.serialize.impl.HessianSerializer;

/**
 * serializer factory
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class MoSerializerFactory {

    /**
     * return current serializer
     */
    public static MoSerializer getSerializer() {
        return new HessianSerializer();
    }
}
