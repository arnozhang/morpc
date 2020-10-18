package com.morpc.demo.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {
    "com.morpc.demo.registry.config"
})
public class MoRpcRegistryDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoRpcRegistryDemoApplication.class, args);
    }
}
