package com.morpc.registry.message.request;

import lombok.Getter;
import lombok.Setter;

/**
 * unregister rpc service request
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class MoUnregisterServiceRequest extends MoBaseRegistryRequest {

    private static final long serialVersionUID = -6368932113448991368L;

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
