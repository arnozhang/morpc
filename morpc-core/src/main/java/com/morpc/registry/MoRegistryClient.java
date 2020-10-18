package com.morpc.registry;

import com.morpc.registry.meta.RpcServiceMetaInfo;

/**
 * registry client
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public interface MoRegistryClient {

    /**
     * register rpc service
     *
     * @param metaInfo rpc service meta information
     * @param service  actual service
     */
    void registerService(RpcServiceMetaInfo metaInfo, Object service);

    /**
     * unregister rpc service
     *
     * @param metaInfo rpc service meta information
     */
    void unregisterService(RpcServiceMetaInfo metaInfo);

    /**
     * discover rpc service from remote register server
     *
     * @param serviceName rpc service name
     * @param version     rpc version
     * @param timeout     discovery timeout
     * @return remote rpc service meta information
     */
    RpcServiceMetaInfo discoveryService(String serviceName, String version, long timeout);
}
