package com.morpc.demo.reference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@SpringBootApplication
@EnableAutoConfiguration
@ImportResource(locations = "classpath:spring/*.xml")
@ComponentScan(basePackages = {
    "com.morpc.spring.*",
    "com.morpc.demo.reference.controller"
})
public class MoRpcReferenceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoRpcReferenceDemoApplication.class, args);
    }
}
