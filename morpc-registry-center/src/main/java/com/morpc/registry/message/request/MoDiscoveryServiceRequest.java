package com.morpc.registry.message.request;

import lombok.Getter;
import lombok.Setter;

/**
 * discovery service request
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class MoDiscoveryServiceRequest extends MoBaseRegistryRequest {

    private static final long serialVersionUID = -7434226405218327132L;

    /**
     * rpc service name
     */
    private String serviceName;

    /**
     * rpc version
     */
    private String version;

    /**
     * discovery timeout
     */
    private long timeout;
}
