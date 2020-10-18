package com.morpc.registry.message;

import com.morpc.common.model.ToString;
import lombok.Getter;
import lombok.Setter;

/**
 * base registry message
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public abstract class MoRegistryMessage extends ToString {

    private static final long serialVersionUID = 3255361841708652070L;

    /**
     * request id
     */
    private String requestId;
}
