package com.morpc.registry.client;

import com.morpc.common.error.MoRpcTimeoutException;
import com.morpc.common.misc.ResultHolder;
import com.morpc.common.netty.MoRpcMessageHandler;
import com.morpc.common.netty.MoRpcNettyClient;
import com.morpc.registry.message.request.MoDiscoveryServiceRequest;
import com.morpc.registry.message.request.MoRegisterServiceRequest;
import com.morpc.registry.message.request.MoUnregisterServiceRequest;
import com.morpc.registry.message.result.MoBaseRegistryResult;
import com.morpc.registry.message.result.MoDiscoveryServiceResult;
import com.morpc.registry.message.result.MoRegisterServiceResult;
import com.morpc.registry.message.result.MoUnRegisterServiceResult;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * builtin registry client
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Slf4j(topic = "RegistryClient")
public class MoRegistryCenterClient {

    /**
     * request client
     */
    private final MoRpcNettyClient<MoBaseRegistryResult> moRpcNettyClient = new MoRpcNettyClient<>();

    /**
     * discovery result holders
     */
    private final Map<String, ResultHolder<MoDiscoveryServiceResult>> discoveryResultHolders = new HashMap<>();

    /**
     * start registry client
     *
     * @param host remote registry server host
     * @param port remote registry server port
     */
    public void startClient(String host, int port) {
        log.info("connect remote registry on: {}:{}", host, port);

        try {
            startClientInternal(host, port);
        } catch (Throwable t) {
            log.error("start MoRegistryCenterClient failed", t);
        }
    }

    /**
     * send registry message
     */
    private void sendMessage(Object msg) throws Exception {
        moRpcNettyClient.sendMessage(msg);
    }

    /**
     * start client internal
     */
    private void startClientInternal(String host, int port) throws Exception {
        moRpcNettyClient.setClientName("RegistryClient");
        moRpcNettyClient.setMessageHandler(new MoRpcMessageHandler<MoBaseRegistryResult>() {

            @Override
            public void handleMessage(
                ChannelHandlerContext ctx, MoBaseRegistryResult result) throws Exception {

                if (result instanceof MoRegisterServiceResult) {
                    log.error("** registerService result: " + result);
                } else if (result instanceof MoUnRegisterServiceResult) {
                    log.error("** unregisterService result: " + result);
                } else if (result instanceof MoDiscoveryServiceResult) {
                    log.error("** discoveryService result: " + result);

                    handleDiscoveryResult((MoDiscoveryServiceResult) result);
                }
            }
        });

        // start client
        moRpcNettyClient.startClient(host, port);
    }

    /**
     * register rpc service
     */
    public void registerService(MoRegisterServiceRequest request) throws Exception {
        log.info("registerService: " + request);

        sendMessage(request);
    }

    /**
     * unregister rpc service
     */
    public void unregisterService(MoUnregisterServiceRequest request) throws Exception {
        log.info("unregisterService: " + request);

        sendMessage(request);
    }

    /**
     * discovery rpc service
     */
    public MoDiscoveryServiceResult discoveryService(MoDiscoveryServiceRequest request) throws Exception {
        log.info("discoveryService: " + request);

        String requestId = UUID.randomUUID().toString();
        request.setRequestId(requestId);

        // add wait lock
        final ResultHolder<MoDiscoveryServiceResult> holder = new ResultHolder<>();
        discoveryResultHolders.put(requestId, holder);

        // send
        sendMessage(request);

        // wait result
        holder.waitResult(request.getTimeout());

        // check timeout
        if (!holder.isNotified()) {
            throw new MoRpcTimeoutException(
                String.format("discovery service timeout: %s@%s",
                    request.getServiceName(), request.getVersion()));
        }

        // return result
        return holder.getResult();
    }

    /**
     * handle discovery rpc service result
     *
     * @param result discovery result
     */
    private void handleDiscoveryResult(MoDiscoveryServiceResult result) {
        if (!result.isSuccess()) {
            // discovery failed
            log.error("discovery service failed: " + result);
            return;
        }

        String key = result.getRequestId();
        ResultHolder<MoDiscoveryServiceResult> resultHolder = discoveryResultHolders.get(key);

        if (resultHolder != null) {
            // notify discovery result
            discoveryResultHolders.remove(key);
            resultHolder.notifyResult(result);
        }
    }
}
