package com.morpc.common.serialize;

/**
 * general serializer
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public interface MoSerializer {

    /**
     * object -> byteArray
     */
    byte[] serialize(Object object) throws Exception;

    /**
     * byteArray -> object
     */
    Object deSerialize(byte[] bytes) throws Exception;
}
