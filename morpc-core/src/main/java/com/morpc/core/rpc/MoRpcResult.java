package com.morpc.core.rpc;

import com.morpc.common.model.ToString;
import lombok.Getter;
import lombok.Setter;

/**
 * rpc result message
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class MoRpcResult extends ToString {

    private static final long serialVersionUID = 3969121563988295372L;

    /**
     * request id. {@link MoRpcRequest#getRequestId()}
     */
    private String requestId;

    /**
     * is rpc invoke success or not
     */
    private boolean success;

    /**
     * actual rpc service result
     */
    private Object result;

    /**
     * rpc invocation errorCode
     */
    private String errorCode;

    /**
     * rpc invocation errorMessage
     */
    private String errorMessage;

    /**
     * build error result
     */
    public static MoRpcResult error(String requestId, String errorMessage) {
        MoRpcResult result = new MoRpcResult();
        result.setSuccess(false);
        result.setRequestId(requestId);
        result.setErrorMessage(errorMessage);

        return result;
    }
}
