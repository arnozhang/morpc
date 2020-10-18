package com.morpc.registry.message.result;

import lombok.Getter;
import lombok.Setter;

/**
 * unregister rpc service result
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class MoUnRegisterServiceResult extends MoBaseRegistryResult {

    private static final long serialVersionUID = 7641553573418127042L;

    /**
     * build a success result
     */
    public static MoUnRegisterServiceResult success() {
        MoUnRegisterServiceResult result = new MoUnRegisterServiceResult();
        result.setSuccess(true);

        return result;
    }
}
