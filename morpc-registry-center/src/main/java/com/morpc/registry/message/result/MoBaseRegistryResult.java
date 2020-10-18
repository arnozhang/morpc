package com.morpc.registry.message.result;

import com.morpc.registry.message.MoRegistryMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * base registry result
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public abstract class MoBaseRegistryResult extends MoRegistryMessage {

    private static final long serialVersionUID = 2363667616963765718L;

    /**
     * handle registry message(register/unregister/discovery/...) success or not
     */
    private boolean success;

    /**
     * error code
     */
    private String errorCode;

    /**
     * error message detail
     */
    private String errorMessage;
}
