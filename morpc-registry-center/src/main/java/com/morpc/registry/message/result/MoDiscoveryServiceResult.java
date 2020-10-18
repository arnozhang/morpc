package com.morpc.registry.message.result;

import lombok.Getter;
import lombok.Setter;

/**
 * discovery registry result
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class MoDiscoveryServiceResult extends MoBaseRegistryResult {

    private static final long serialVersionUID = 2363667616963765718L;

    /**
     * rpc service server host
     */
    private String host;

    /**
     * rpc service server port
     */
    private String port;

    /**
     * build a error result
     */
    public static MoDiscoveryServiceResult error(String errorMessage) {
        MoDiscoveryServiceResult result = new MoDiscoveryServiceResult();
        result.setSuccess(false);
        result.setErrorMessage(errorMessage);

        return result;
    }
}
