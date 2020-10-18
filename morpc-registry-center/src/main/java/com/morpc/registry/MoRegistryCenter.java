package com.morpc.registry;

/**
 * registry center
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public interface MoRegistryCenter {

    /**
     * start registry center on port
     *
     * @param port registry server port
     */
    void start(int port);
}
