package com.morpc.demo.service.declare.list;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class DemoBeansListServiceImpl implements DemoBeansListService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Map<String, String> getBeansList() {
        Map<String, String> beans = new HashMap<>();
        for (String name : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(name);
            beans.put(name, bean.getClass().getName());
        }

        return beans;
    }
}
