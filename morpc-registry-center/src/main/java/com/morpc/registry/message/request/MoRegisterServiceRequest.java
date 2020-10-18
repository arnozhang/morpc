package com.morpc.registry.message.request;

import lombok.Getter;
import lombok.Setter;

/**
 * register rpc service request
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class MoRegisterServiceRequest extends MoBaseRegistryRequest {

    private static final long serialVersionUID = 267858645635293742L;

    /**
     * rpc service name
     */
    private String serviceName;

    /**
     * rpc version
     */
    private String version;

    /**
     * rpc service server host
     */
    private String host;

    /**
     * rpc service server port
     */
    private String port;
}
