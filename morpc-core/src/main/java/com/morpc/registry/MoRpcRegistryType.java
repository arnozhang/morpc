package com.morpc.registry;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * rpc registry type
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@AllArgsConstructor
public enum MoRpcRegistryType {

    /**
     * built in registry
     */
    MoRpc("morpc", "MoRpc builtin registry impl"),

    /**
     * ZooKeeper registry
     */
    ZooKeeper("zookeeper", "ZooKeeper registry impl");

    /**
     * registry type
     */
    private final String type;

    /**
     * description
     */
    private final String desc;
}
