package com.morpc.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * spring registry configuration
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Data
@ConfigurationProperties(prefix = "morpc.registry")
public class MoRegistryProperties {

    /**
     * registry server url. such as:
     *
     * - morpc://localhost:7777
     * - zookeeper://localhost:7777
     */
    private String url;
}
