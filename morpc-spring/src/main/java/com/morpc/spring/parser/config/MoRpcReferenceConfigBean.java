package com.morpc.spring.parser.config;

import com.morpc.constants.MoRpcConstants;
import com.morpc.spring.helper.MoRpcReferenceHelper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * <morpc:reference id="referenceBean" interface="com.xx.xx"/>
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class MoRpcReferenceConfigBean extends MoRpcBaseConfigBean implements BeanFactoryAware {

    /**
     * reference proxy bean id
     */
    private String id;

    /**
     * rpc service name
     */
    private String interfaceType;

    /**
     * rpc invoke timeout
     */
    private String timeout;

    /**
     * bean factory
     */
    private BeanFactory beanFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        long invokeTimeout = timeout == null || timeout.isEmpty()
            ? MoRpcConstants.DEFAULT_RPC_INVOKE_TIMEOUT
            : Long.parseLong(timeout);

        // create reference proxy
        MoRpcReferenceHelper moRpcReferenceHelper = applicationContext.getBean(
            "moRpcReferenceHelper", MoRpcReferenceHelper.class);
        Object referenceProxy = moRpcReferenceHelper.createRpcReference(
            interfaceType, MoRpcConstants.CURRENT_RPC_VERSION, invokeTimeout);

        // register bean
        ConfigurableBeanFactory factory = (ConfigurableBeanFactory) beanFactory;
        factory.registerSingleton(id, referenceProxy);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
