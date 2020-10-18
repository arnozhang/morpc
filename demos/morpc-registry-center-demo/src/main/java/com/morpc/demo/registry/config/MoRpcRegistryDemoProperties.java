package com.morpc.demo.registry.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Data
@ConfigurationProperties(prefix = "morpc.registry")
public class MoRpcRegistryDemoProperties {

    private String port;
}
