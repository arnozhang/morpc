package com.morpc.core.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * rpc service impl manager
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class MoRpcServiceImplManager {

    /**
     * rpc impl services
     */
    private static final Map<String, Object> rpcServices = new HashMap<>();

    /**
     * add a new rpc service impl
     */
    public static void addServiceImpl(String serviceKey, Object service) {
        rpcServices.put(serviceKey, service);
    }

    /**
     * remove rpc service impl
     */
    public static void removeServiceImpl(String serviceKey) {
        rpcServices.remove(serviceKey);
    }

    /**
     * find/get rpc service impl
     */
    public static Object getServiceImpl(String serviceKey) {
        return rpcServices.get(serviceKey);
    }
}
