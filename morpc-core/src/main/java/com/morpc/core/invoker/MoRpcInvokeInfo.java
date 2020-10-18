package com.morpc.core.invoker;

import lombok.Data;

/**
 * mo rpc invoke info
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Data
public class MoRpcInvokeInfo {

    /**
     * rpc interfaceType
     */
    private final Class<?> interfaceType;

    /**
     * rpc version
     */
    private final String version;

    /**
     * rpc invoke timeout
     */
    private final long timeout;

    public MoRpcInvokeInfo(Class<?> interfaceType, String version, long timeout) {
        this.interfaceType = interfaceType;
        this.version = version;
        this.timeout = timeout;
    }
}
