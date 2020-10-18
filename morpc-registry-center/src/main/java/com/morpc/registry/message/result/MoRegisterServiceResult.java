package com.morpc.registry.message.result;

import lombok.Getter;
import lombok.Setter;

/**
 * register rpc service result
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class MoRegisterServiceResult extends MoBaseRegistryResult {

    private static final long serialVersionUID = -8841960608142979387L;

    /**
     * build a success result
     */
    public static MoRegisterServiceResult success() {
        MoRegisterServiceResult result = new MoRegisterServiceResult();
        result.setSuccess(true);

        return result;
    }
}
