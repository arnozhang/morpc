package com.morpc.demo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@SpringBootApplication
@ImportResource(locations = "classpath:spring/*.xml")
@ComponentScan(basePackages = {
    "com.morpc.spring.*",
    "com.morpc.demo.service.*"
})
public class MoRpcServiceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoRpcServiceDemoApplication.class, args);
    }
}
