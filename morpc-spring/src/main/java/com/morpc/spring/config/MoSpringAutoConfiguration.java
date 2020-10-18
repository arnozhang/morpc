package com.morpc.spring.config;

import com.morpc.core.invoker.MoRpcInvoker;
import com.morpc.core.provider.MoRpcProvider;
import com.morpc.registry.MoRegistryClient;
import com.morpc.registry.factory.MoRegistryClientFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * spring auto configuration
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Configuration
@EnableConfigurationProperties({
    MoRpcProperties.class,
    MoRegistryProperties.class
})
public class MoSpringAutoConfiguration {

    /**
     * rpc service properties
     */
    @Resource
    private MoRpcProperties moRpcProperties;

    /**
     * rpc registry properties
     */
    @Resource
    private MoRegistryProperties moRegistryProperties;

    @Bean
    public MoRegistryClient moRegistryClient() {
        return MoRegistryClientFactory.createInstance(moRegistryProperties.getUrl());
    }

    @Bean
    public MoRpcInvoker moRpcInvoker() {
        MoRpcInvoker invoker = new MoRpcInvoker();
        invoker.setRegistryClient(moRegistryClient());

        return invoker;
    }

    @Bean
    public MoRpcProvider moRpcProvider() {
        MoRpcProvider provider = new MoRpcProvider();

        // start local rpc provider
        provider.startProvider(Integer.parseInt(moRpcProperties.getPort()));

        return provider;
    }
}
