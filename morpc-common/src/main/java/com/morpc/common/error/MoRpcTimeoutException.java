package com.morpc.common.error;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class MoRpcTimeoutException extends MoRpcException {

    public MoRpcTimeoutException(String message) {
        super(message);
    }
}
