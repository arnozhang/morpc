package com.morpc.core.rpc;

import com.morpc.common.model.ToString;
import lombok.Getter;
import lombok.Setter;

/**
 * rpc request message
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class MoRpcRequest extends ToString {

    private static final long serialVersionUID = 2540573222814207414L;

    /**
     * request id
     */
    private String requestId;

    /**
     * rpc version
     */
    private String version;

    /**
     * rpc invoke timeout. ms
     */
    private long timeout;

    /**
     * remote service name
     */
    private String serviceName;

    /**
     * remote service method
     */
    private String methodName;

    /**
     * request arguments
     */
    private Object[] args;

    /**
     * request arguments' types
     */
    private Class<?>[] argsTypes;
}
