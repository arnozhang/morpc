package com.morpc.registry.impl;

import com.morpc.core.MoRpc;
import com.morpc.core.provider.MoRpcServiceImplManager;
import com.morpc.registry.MoRegistryClient;
import com.morpc.registry.meta.RpcServiceMetaInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * base registry client
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Slf4j(topic = "RegistryClient")
public abstract class MoBaseRegistryClient implements MoRegistryClient {

    @Override
    public final void registerService(RpcServiceMetaInfo metaInfo, Object service) {
        String serviceKey = MoRpc.buildServiceKey(metaInfo.getServiceName(), metaInfo.getVersion());
        log.info("registryService: {}", serviceKey);

        MoRpcServiceImplManager.addServiceImpl(serviceKey, service);

        registryServiceInternal(metaInfo);
    }

    @Override
    public final void unregisterService(RpcServiceMetaInfo metaInfo) {
        String serviceKey = MoRpc.buildServiceKey(metaInfo.getServiceName(), metaInfo.getVersion());
        log.info("unregisterService: {}", serviceKey);

        MoRpcServiceImplManager.removeServiceImpl(serviceKey);

        unRegistryServiceInternal(metaInfo);
    }

    protected abstract void registryServiceInternal(RpcServiceMetaInfo metaInfo);

    protected abstract void unRegistryServiceInternal(RpcServiceMetaInfo metaInfo);
}
