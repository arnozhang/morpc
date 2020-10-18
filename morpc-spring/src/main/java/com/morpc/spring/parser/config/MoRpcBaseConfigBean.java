package com.morpc.spring.parser.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * base config bean
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public abstract class MoRpcBaseConfigBean implements InitializingBean, ApplicationContextAware {

    /**
     * applicationContext
     */
    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
