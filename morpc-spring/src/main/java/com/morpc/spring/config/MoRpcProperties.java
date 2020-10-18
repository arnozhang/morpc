package com.morpc.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * spring rpc properties
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Data
@ConfigurationProperties(prefix = "morpc")
public class MoRpcProperties {

    /**
     * rpc service port
     */
    private String port;
}
