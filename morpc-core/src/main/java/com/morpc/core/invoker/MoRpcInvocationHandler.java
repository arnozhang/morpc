package com.morpc.core.invoker;

import com.morpc.common.error.MoRpcException;
import com.morpc.core.rpc.MoRpcRequest;
import com.morpc.core.rpc.MoRpcResult;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * rpc invocation proxy handler
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class MoRpcInvocationHandler implements InvocationHandler {

    /**
     * rpc invoker
     */
    private final MoRpcInvoker moRpcInvoker;

    /**
     * rpc invoke info
     */
    private final MoRpcInvokeInfo rpcInvokeInfo;

    public MoRpcInvocationHandler(
        MoRpcInvoker moRpcInvoker, MoRpcInvokeInfo rpcInvokeInfo) {

        this.moRpcInvoker = moRpcInvoker;
        this.rpcInvokeInfo = rpcInvokeInfo;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        String interfaceName = rpcInvokeInfo.getInterfaceType().getName();

        if (method.getDeclaringClass() == Object.class) {
            // Object.xxx methods
            switch (name) {
                case "equals":
                    return proxy == args[0];
                case "hashCode":
                    return System.identityHashCode(proxy);
                case "toString":
                    return proxy.getClass().getName() + "@"
                        + Integer.toHexString(System.identityHashCode(proxy));
                default:
                    throw new MoRpcException("Unknown method: " + name);
            }
        }

        // build request
        MoRpcRequest request = new MoRpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setServiceName(interfaceName);
        request.setVersion(rpcInvokeInfo.getVersion());
        request.setTimeout(rpcInvokeInfo.getTimeout());
        request.setMethodName(name);
        request.setArgs(args);
        request.setArgsTypes(method.getParameterTypes());

        // invoke rpc service
        MoRpcResult result = moRpcInvoker.invokeRpc(request);

        // result check
        if (result == null) {
            throw new MoRpcException(String.format(
                "invoke rpc failed: interface = %s, method = %s", interfaceName, name));
        } else if (!result.isSuccess()) {
            throw new MoRpcException(String.format(
                "invoke rpc failed: interface = %s, method = %s, error = %s",
                interfaceName, name, result.getErrorMessage()));
        }

        return result.getResult();
    }
}
