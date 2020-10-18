package com.morpc.registry.message.request;

import com.morpc.registry.message.MoRegistryMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * base registry request
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public abstract class MoBaseRegistryRequest extends MoRegistryMessage {

    private static final long serialVersionUID = -7434226405218327132L;
}
