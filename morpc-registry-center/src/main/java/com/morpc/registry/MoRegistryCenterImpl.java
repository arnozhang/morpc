package com.morpc.registry;

import com.morpc.common.netty.MoRpcMessageHandler;
import com.morpc.common.netty.MoRpcNettyServer;
import com.morpc.registry.message.request.MoBaseRegistryRequest;
import com.morpc.registry.message.request.MoDiscoveryServiceRequest;
import com.morpc.registry.message.request.MoRegisterServiceRequest;
import com.morpc.registry.message.request.MoUnregisterServiceRequest;
import com.morpc.registry.message.result.MoBaseRegistryResult;
import com.morpc.registry.message.result.MoDiscoveryServiceResult;
import com.morpc.registry.message.result.MoRegisterServiceResult;
import com.morpc.registry.message.result.MoUnRegisterServiceResult;
import com.morpc.registry.model.MoRegistryServiceInfo;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.*;

/**
 * builtin registry center impl
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Slf4j(topic = "Registry")
public class MoRegistryCenterImpl implements MoRegistryCenter {

    /**
     * registered services
     */
    private static final Map<String, Map<String, MoRegistryServiceInfo>> registeredServices = new HashMap<>();

    @Override
    public void start(int port) {
        log.info("[Registry] will start MoRegistryCenterImpl");

        try {
            startInternal(port);
        } catch (Throwable t) {
            log.error("[Registry] start MoRegistryCenterImpl error", t);
        }
    }

    /**
     * start registry server
     */
    private void startInternal(int port) throws Exception {
        // create server
        MoRpcNettyServer<MoBaseRegistryRequest> server = new MoRpcNettyServer<>();
        server.setServerName("Registry");
        server.setMessageHandler(new MoRpcMessageHandler<MoBaseRegistryRequest>() {

            @Override
            public void handleMessage(
                ChannelHandlerContext ctx, MoBaseRegistryRequest request) throws Exception {

                MoBaseRegistryResult result = null;

                if (request instanceof MoRegisterServiceRequest) {
                    result = registerService((MoRegisterServiceRequest) request);
                } else if (request instanceof MoUnregisterServiceRequest) {
                    result = unregisterService((MoUnregisterServiceRequest) request);
                } else if (request instanceof MoDiscoveryServiceRequest) {
                    result = discoveryService((MoDiscoveryServiceRequest) request);
                }

                if (result != null) {
                    result.setRequestId(request.getRequestId());
                    ctx.writeAndFlush(result).sync();
                }
            }
        });

        // start registry server
        String host = InetAddress.getLocalHost().getHostAddress();
        server.startServer(host, port);
    }

    /**
     * build rpc service key
     */
    private static String buildServiceKey(String serviceName, String version) {
        return String.format("mo-registry-%s@%s", serviceName, version);
    }

    /**
     * build rpc service target-node key
     */
    private static String buildServiceTargetNode(String host, String port) {
        return String.format("mo-registry-target-%s@%s", host, port);
    }

    /**
     * handle register rpc service request
     */
    private MoRegisterServiceResult registerService(MoRegisterServiceRequest request) {
        String key = buildServiceKey(request.getServiceName(), request.getVersion());
        log.info("[Registry] registerService: key = {}, details = {}", key, request);

        Map<String, MoRegistryServiceInfo> map = registeredServices.computeIfAbsent(
            key, k -> new HashMap<>());

        MoRegistryServiceInfo info = new MoRegistryServiceInfo();
        info.setHost(request.getHost());
        info.setPort(request.getPort());

        String target = buildServiceTargetNode(request.getHost(), request.getPort());
        map.put(target, info);

        return MoRegisterServiceResult.success();
    }

    /**
     * handle unregister rpc service request
     */
    private MoUnRegisterServiceResult unregisterService(MoUnregisterServiceRequest request) {
        String key = buildServiceKey(request.getServiceName(), request.getVersion());
        log.info("[Registry] unregisterService: key = {}, details = {}", key, request);

        Map<String, MoRegistryServiceInfo> map = registeredServices.get(key);
        if (map != null) {
            String target = buildServiceTargetNode(request.getHost(), request.getPort());
            map.remove(target);
        }

        return MoUnRegisterServiceResult.success();
    }

    /**
     * handle discovery rpc service request
     */
    private MoDiscoveryServiceResult discoveryService(MoDiscoveryServiceRequest request) {
        String key = buildServiceKey(request.getServiceName(), request.getVersion());
        log.info("[Registry] discoveryService: %s" + key);

        // check service target list
        Map<String, MoRegistryServiceInfo> map = registeredServices.get(key);
        if (map == null || map.isEmpty()) {
            log.warn("[Registry] discoveryService returns null: %s" + key);
            return MoDiscoveryServiceResult.error("cannot find service: " + key);
        }

        // random
        List<MoRegistryServiceInfo> nodes = new ArrayList<>(map.values());
        MoRegistryServiceInfo info = nodes.get(new Random().nextInt(nodes.size()));

        log.info(
            "[Registry] discoveryService success! totalFindCount = {}, random return = {}:{}",
            map.size(), info.getHost(), info.getPort());

        // build discovery result
        MoDiscoveryServiceResult result = new MoDiscoveryServiceResult();
        result.setSuccess(true);
        result.setHost(info.getHost());
        result.setPort(info.getPort());

        return result;
    }
}
