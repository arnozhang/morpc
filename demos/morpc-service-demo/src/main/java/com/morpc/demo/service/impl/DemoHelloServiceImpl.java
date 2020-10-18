package com.morpc.demo.service.impl;

import com.morpc.annotations.MoService;
import com.morpc.demo.service.declare.hello.DemoHelloService;
import com.morpc.demo.service.declare.hello.HelloRequest;
import com.morpc.demo.service.declare.hello.HelloResult;
import org.springframework.stereotype.Service;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@MoService
@Service
public class DemoHelloServiceImpl implements DemoHelloService {

    @Override
    public HelloResult sayHello(HelloRequest request) {
        System.out.println("[DemoHelloServiceImpl] hello: " + request.getMessage());

        HelloResult result = new HelloResult();
        result.setResult("hello " + request.getMessage());

        return result;
    }
}
