package com.morpc.demo.reference.controller;

import com.alibaba.fastjson.JSONObject;
import com.morpc.annotations.MoReference;
import com.morpc.demo.service.declare.hello.DemoHelloService;
import com.morpc.demo.service.declare.hello.HelloRequest;
import com.morpc.demo.service.declare.hello.HelloResult;
import com.morpc.demo.service.declare.list.DemoBeansListService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@RestController
public class MoRpcReferenceTestController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @MoReference
    private DemoHelloService demoHelloService;

    @Resource
    private DemoBeansListService demoBeansListServiceRef;

    @GetMapping("/test")
    public String test(@RequestParam(value = "message", defaultValue = "morpc demo") String message) {
        HelloRequest request = new HelloRequest();
        request.setMessage(message);

        try {
            HelloResult result = demoHelloService.sayHello(request);
            System.out.printf("[Reference-Controller] %s\n", result.getResult());

            JSONObject resultInfo = new JSONObject();
            resultInfo.put("request", message);
            resultInfo.put("result", result.getResult());
            resultInfo.put("date", new Date().toString());

            return resultInfo.toJSONString();
        } catch (Throwable t) {
            t.printStackTrace();

            return t.toString();
        }
    }

    @GetMapping("/beans")
    public String beans() {
        JSONObject result = new JSONObject();
        result.put("date", new Date().toString());
        result.put("referenceDemoBeans", getBeansList());
        result.put("serviceDemoBeans", demoBeansListServiceRef.getBeansList());

        return result.toJSONString();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Map<String, String> getBeansList() {
        Map<String, String> beans = new HashMap<>();
        for (String name : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(name);
            beans.put(name, bean.getClass().getName());
        }

        return beans;
    }
}
