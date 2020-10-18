package com.morpc.registry.factory;

import com.morpc.registry.MoRegistryClient;
import com.morpc.registry.MoRpcRegistryType;
import com.morpc.registry.impl.MoBuiltinRegistryClient;
import com.morpc.registry.impl.ZooKeeperRegistryClient;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * rpc registry client factory
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Slf4j(topic = "RegistryClientFactory")
public class MoRegistryClientFactory {

    /**
     * create registry client
     *
     * @param url registry server url
     */
    public static MoRegistryClient createInstance(String url) {
        URI uri = URI.create(url);
        String scheme = uri.getScheme();

        log.info("[RegistryClientFactory] will connect registry: " + url);

        if (MoRpcRegistryType.MoRpc.getType().equals(scheme)) {
            return new MoBuiltinRegistryClient(uri);
        } else if (MoRpcRegistryType.ZooKeeper.getType().equals(scheme)) {
            return new ZooKeeperRegistryClient(uri);
        }

        throw new RuntimeException("Cannot create registry center: " + scheme);
    }
}
