package com.morpc.core.provider;

import com.morpc.common.error.MoRpcException;
import com.morpc.common.netty.MoRpcMessageHandler;
import com.morpc.common.netty.MoRpcNettyServer;
import com.morpc.constants.MoRpcConstants;
import com.morpc.core.MoRpc;
import com.morpc.core.rpc.MoRpcRequest;
import com.morpc.core.rpc.MoRpcResult;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.net.InetAddress;

/**
 * rpc service provider
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Slf4j
public class MoRpcProvider {

    /**
     * rpc service port
     */
    private int rpcPort;

    /**
     * start rpc service on port
     *
     * @param port rpc service port
     */
    public void startProvider(int port) {
        this.rpcPort = port;

        new Thread(() -> {
            log.info("[Provider] will start rpc provider watcher");

            try {
                startProviderInternal(port);
            } catch (Throwable t) {
                log.error("[Provider] start rpc provider watcher error", t);
            }
        }).start();
    }

    /**
     * start server
     *
     * @param port rpc service port
     */
    private void startProviderInternal(int port) throws Exception {
        // create server
        MoRpcNettyServer<MoRpcRequest> server = new MoRpcNettyServer<>();
        server.setServerName("Provider");
        server.setMessageHandler(new MoRpcMessageHandler<MoRpcRequest>() {

            @Override
            public void handleMessage(
                ChannelHandlerContext ctx, MoRpcRequest request) throws Exception {

                ctx.writeAndFlush(handleRpcRequest(request)).sync();
            }
        });

        // get current host
        String host = InetAddress.getLocalHost().getHostAddress();

        // check port
        if (port <= 0) {
            port = MoRpcConstants.DEFAULT_RPC_SERVICE_PORT;
        }

        // start server
        server.startServer(host, port);
    }

    /**
     * handle rpc request
     *
     * @param request rpc request object
     * @return rpc result
     */
    private MoRpcResult handleRpcRequest(MoRpcRequest request) {
        MoRpcResult result = new MoRpcResult();
        result.setRequestId(request.getRequestId());

        try {
            // invoke
            result.setResult(invokeRpcService(request));

            // result
            result.setSuccess(true);
        } catch (Throwable t) {
            log.error("[Provider] handleRpcRequest error", t);

            // failure message
            result.setErrorMessage(t.toString());
        }

        return result;
    }

    /**
     * invoke rpc service
     *
     * @param request rpc request object
     * @return rpc result
     */
    private Object invokeRpcService(MoRpcRequest request) throws Exception {
        String serviceKey = MoRpc.buildServiceKey(request.getServiceName(), request.getVersion());

        // find service impl
        Object service = MoRpcServiceImplManager.getServiceImpl(serviceKey);
        if (service == null) {
            throw new MoRpcException("Cannot find Rpc service: " + serviceKey);
        }

        // check service type
        Class<?> interfaceType = Class.forName(request.getServiceName());
        if (!interfaceType.isInstance(service)) {
            throw new MoRpcException(String.format(
                "service object not %s instance. service real type: %s",
                interfaceType.getName(), service.getClass().getName()));
        }

        log.info("[Provider] will invoke service: {}\n", request);

        // find invoke method
        Method method = service.getClass().getDeclaredMethod(
            request.getMethodName(), request.getArgsTypes());

        // invoke service method
        method.setAccessible(true);
        Object result = method.invoke(service, request.getArgs());

        log.info(
            "[Provider] invoke service result: class = {}, result = {}\n",
            result.getClass().getName(), result);

        return result;
    }
}
