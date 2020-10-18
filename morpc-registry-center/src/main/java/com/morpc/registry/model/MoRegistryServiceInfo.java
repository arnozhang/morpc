package com.morpc.registry.model;

import lombok.Data;

import java.io.Serializable;

/**
 * registry service information
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Data
public class MoRegistryServiceInfo implements Serializable {

    private static final long serialVersionUID = -2063133284790712471L;

    /**
     * rpc service server host
     */
    private String host;

    /**
     * rpc service server port
     */
    private String port;
}
