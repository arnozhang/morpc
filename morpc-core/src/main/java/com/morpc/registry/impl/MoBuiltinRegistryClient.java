package com.morpc.registry.impl;

import com.morpc.registry.client.MoRegistryCenterClient;
import com.morpc.registry.message.request.MoDiscoveryServiceRequest;
import com.morpc.registry.message.request.MoRegisterServiceRequest;
import com.morpc.registry.message.request.MoUnregisterServiceRequest;
import com.morpc.registry.message.result.MoDiscoveryServiceResult;
import com.morpc.registry.meta.RpcServiceMetaInfo;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * builtin registry client
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Slf4j
public class MoBuiltinRegistryClient extends MoBaseRegistryClient {

    /**
     * builtin registry client
     */
    private final MoRegistryCenterClient moRegistryClient = new MoRegistryCenterClient();

    public MoBuiltinRegistryClient(URI uri) {
        String host = uri.getHost();
        int port = uri.getPort();

        moRegistryClient.startClient(host, port);
    }

    @Override
    public RpcServiceMetaInfo discoveryService(String serviceName, String version, long timeout) {
        MoDiscoveryServiceRequest request = new MoDiscoveryServiceRequest();
        request.setServiceName(serviceName);
        request.setVersion(version);
        request.setTimeout(timeout);

        try {
            MoDiscoveryServiceResult result = moRegistryClient.discoveryService(request);
            if (result != null) {
                RpcServiceMetaInfo metaInfo = new RpcServiceMetaInfo();
                metaInfo.setServiceName(serviceName);
                metaInfo.setVersion(version);
                metaInfo.setHost(result.getHost());
                metaInfo.setPort(result.getPort());

                return metaInfo;
            }
        } catch (Throwable t) {
            log.error("discoveryService error", t);
        }

        return null;
    }

    @Override
    protected void registryServiceInternal(RpcServiceMetaInfo metaInfo) {
        MoRegisterServiceRequest request = new MoRegisterServiceRequest();
        request.setServiceName(metaInfo.getServiceName());
        request.setVersion(metaInfo.getVersion());
        request.setHost(metaInfo.getHost());
        request.setPort(metaInfo.getPort());

        try {
            moRegistryClient.registerService(request);
        } catch (Throwable t) {
            log.error("registryServiceInternal error", t);
        }
    }

    @Override
    protected void unRegistryServiceInternal(RpcServiceMetaInfo metaInfo) {
        MoUnregisterServiceRequest request = new MoUnregisterServiceRequest();
        request.setServiceName(metaInfo.getServiceName());
        request.setVersion(metaInfo.getVersion());
        request.setHost(metaInfo.getHost());
        request.setPort(metaInfo.getPort());

        try {
            moRegistryClient.unregisterService(request);
        } catch (Throwable t) {
            log.error("unRegistryServiceInternal error", t);
        }
    }
}
