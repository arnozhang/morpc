package com.morpc.common.error;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class MoRpcException extends RuntimeException {

    public MoRpcException(String message) {
        super(message);
    }

    public MoRpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public MoRpcException(Throwable cause) {
        super(cause);
    }
}
