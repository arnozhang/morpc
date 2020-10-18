package com.morpc.registry.meta;

import com.morpc.common.model.ToString;
import lombok.Getter;
import lombok.Setter;

/**
 * rpc service meta information
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class RpcServiceMetaInfo extends ToString {

    private static final long serialVersionUID = -6786876437358905866L;

    /**
     * rpc service name
     */
    private String serviceName;

    /**
     * rpc version
     */
    private String version;

    /**
     * rpc server host
     */
    private String host;

    /**
     * rpc server port
     */
    private String port;
}
