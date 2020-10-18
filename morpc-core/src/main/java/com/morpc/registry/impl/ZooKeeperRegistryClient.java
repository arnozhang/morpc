package com.morpc.registry.impl;

import com.morpc.registry.meta.RpcServiceMetaInfo;

import java.net.URI;

/**
 * registry client implemented by ZooKeeper
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class ZooKeeperRegistryClient extends MoBaseRegistryClient {

    public ZooKeeperRegistryClient(URI uri) {
    }

    @Override
    public RpcServiceMetaInfo discoveryService(String serviceName, String version, long timeout) {
        return null;
    }

    @Override
    protected void registryServiceInternal(RpcServiceMetaInfo metaInfo) {
    }

    @Override
    protected void unRegistryServiceInternal(RpcServiceMetaInfo metaInfo) {
    }
}
