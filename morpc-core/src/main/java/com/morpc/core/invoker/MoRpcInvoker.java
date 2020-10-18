package com.morpc.core.invoker;

import com.morpc.common.error.MoRpcException;
import com.morpc.common.error.MoRpcTimeoutException;
import com.morpc.common.misc.ResultHolder;
import com.morpc.common.netty.MoRpcMessageHandler;
import com.morpc.common.netty.MoRpcNettyClient;
import com.morpc.constants.MoRpcConstants;
import com.morpc.core.MoRpc;
import com.morpc.core.rpc.MoRpcRequest;
import com.morpc.core.rpc.MoRpcResult;
import com.morpc.registry.MoRegistryClient;
import com.morpc.registry.meta.RpcServiceMetaInfo;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * rpc invoker
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
@Slf4j
public class MoRpcInvoker {

    /**
     * registry client
     */
    private MoRegistryClient registryClient;

    /**
     * invoke rpc service
     *
     * @param request rpc request
     */
    public MoRpcResult invokeRpc(MoRpcRequest request) throws Exception {
        long timeout = request.getTimeout();
        if (timeout <= 0) {
            timeout = MoRpcConstants.DEFAULT_RPC_INVOKE_TIMEOUT;
        }

        // discovery remote service service
        String serviceName = request.getServiceName();
        RpcServiceMetaInfo metaInfo = registryClient.discoveryService(
            serviceName, request.getVersion(), timeout);

        String serviceKey = MoRpc.buildServiceKey(serviceName, request.getVersion());
        log.info("[Invoker] will invoke rpc: {}, \nrequest = {}\n", serviceKey, request);

        // remote service check
        if (metaInfo == null) {
            log.error("[Invoker] cannot find remote service: " + serviceKey);
            throw new MoRpcException("Cannot find service: " + serviceKey);
        }

        final ResultHolder<MoRpcResult> resultHolder = new ResultHolder<>();
        final MoRpcNettyClient<MoRpcResult> nettyClient = new MoRpcNettyClient<>();

        try {
            nettyClient.setClientName("Invoker");
            nettyClient.setMessageHandler(new MoRpcMessageHandler<MoRpcResult>() {

                @Override
                public void handleMessage(
                    ChannelHandlerContext ctx, MoRpcResult result) throws Exception {

                    resultHolder.notifyResult(result);
                }
            });

            // start client
            nettyClient.startClient(metaInfo.getHost(), Integer.parseInt(metaInfo.getPort()));

            // send request
            nettyClient.sendMessage(request);

            // wait result
            resultHolder.waitResult(timeout);

            if (!resultHolder.isNotified()) {
                // wait timeout
                throw new MoRpcTimeoutException(String.format(
                    "invoke rpc timeout. interface = %s, method = %s, timeout = %s",
                    serviceName, request.getMethodName(), timeout));
            }

            // return result
            MoRpcResult result = resultHolder.getResult();
            log.info("[Invoker] invoke rpc success: {}, \nresult = {}\n", serviceKey, result);

            return result;
        } catch (Throwable t) {
            log.error("[Invoker] invoke rpc error", t);

            throw t instanceof MoRpcException ? (MoRpcException) t : new MoRpcException(t);
        } finally {
            // stop client
            nettyClient.stopClient();
        }
    }
}
