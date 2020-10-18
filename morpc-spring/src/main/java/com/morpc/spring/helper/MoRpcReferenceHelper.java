package com.morpc.spring.helper;

import com.morpc.core.MoRpc;
import com.morpc.core.invoker.MoRpcInvokeInfo;
import com.morpc.core.invoker.MoRpcInvoker;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * rpc reference helper
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Component
public class MoRpcReferenceHelper {

    /**
     * rpc reference invoker
     */
    @Resource
    private MoRpcInvoker moRpcInvoker;

    /**
     * create rpc reference proxy
     */
    public Object createRpcReference(String serviceName, String version, long timeout) throws Exception {
        Class<?> interfaceType = Class.forName(serviceName);
        return createRpcReference(interfaceType, version, timeout);
    }

    /**
     * create rpc reference proxy
     */
    public Object createRpcReference(Class<?> interfaceType, String version, long timeout) throws Exception {
        MoRpcInvokeInfo invokeInfo = new MoRpcInvokeInfo(interfaceType, version, timeout);
        return MoRpc.getServiceReference(moRpcInvoker, invokeInfo);
    }
}
