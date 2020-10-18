package com.morpc.demo.registry.config;

import com.morpc.registry.MoRegistryCenter;
import com.morpc.registry.MoRegistryCenterImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Configuration
@EnableConfigurationProperties({
    MoRpcRegistryDemoProperties.class
})
public class MoRpcRegistryDemoConfiguration {

    @Resource
    private MoRpcRegistryDemoProperties moRpcRegistryDemoProperties;

    @Bean
    public MoRegistryCenter moRegistryCenter() {
        MoRegistryCenter registryCenter = new MoRegistryCenterImpl();
        registryCenter.start(Integer.parseInt(moRpcRegistryDemoProperties.getPort()));

        return registryCenter;
    }
}
