package com.morpc.spring.helper;

import com.morpc.registry.MoRegistryClient;
import com.morpc.registry.meta.RpcServiceMetaInfo;
import com.morpc.spring.config.MoRpcProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.FatalBeanException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;

/**
 * rpc register helper
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Component
@Slf4j
public class MoRpcRegisterHelper {

    /**
     * rpc service properties
     */
    @Resource
    private MoRpcProperties moRpcProperties;

    /**
     * registry client
     */
    @Resource
    private MoRegistryClient moRegistryClient;

    /**
     * register rpc service
     */
    public void registerService(String serviceName, String version, Object service) {
        RpcServiceMetaInfo metaInfo = new RpcServiceMetaInfo();
        metaInfo.setServiceName(serviceName);
        metaInfo.setVersion(version);

        try {
            metaInfo.setHost(InetAddress.getLocalHost().getHostAddress());
            metaInfo.setPort(moRpcProperties.getPort());

            moRegistryClient.registerService(metaInfo, service);
        } catch (Throwable t) {
            String message = String.format(
                "Cannot register service: %s, error: %s", serviceName, t);
            log.error(message, t);

            throw new FatalBeanException(message);
        }
    }
}
