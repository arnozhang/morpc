package com.morpc.core;

import com.morpc.core.invoker.MoRpcInvocationHandler;
import com.morpc.core.invoker.MoRpcInvokeInfo;
import com.morpc.core.invoker.MoRpcInvoker;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * rpc utils
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class MoRpc {

    /**
     * cached service proxy
     */
    private static final Map<String, Object> serviceProxies = new HashMap<>();

    /**
     * build rpc service key
     *
     * @param serviceName service name
     * @param version     rpc version
     */
    public static String buildServiceKey(String serviceName, String version) {
        return String.format("mo-rpc-%s@%s", serviceName, version);
    }

    /**
     * build rpc service key
     *
     * @param interfaceType service interface
     * @param version       rpc version
     */
    public static String buildServiceKey(Class<?> interfaceType, String version) {
        return buildServiceKey(interfaceType.getName(), version);
    }

    /**
     * get/create rpc reference proxy
     *
     * @param moRpcInvoker  rpc invoker
     * @param rpcInvokeInfo rpc invoke info
     * @return remote service proxy
     */
    public static Object getServiceReference(
        MoRpcInvoker moRpcInvoker, MoRpcInvokeInfo rpcInvokeInfo) {

        Class<?> interfaceType = rpcInvokeInfo.getInterfaceType();
        String serviceKey = buildServiceKey(interfaceType, rpcInvokeInfo.getVersion());
        Object proxy = serviceProxies.get(serviceKey);

        if (proxy == null) {
            // create new proxy
            proxy = Proxy.newProxyInstance(interfaceType.getClassLoader(),
                new Class<?>[] {interfaceType,},
                new MoRpcInvocationHandler(moRpcInvoker, rpcInvokeInfo));

            serviceProxies.put(serviceKey, proxy);
        }

        return proxy;
    }
}
