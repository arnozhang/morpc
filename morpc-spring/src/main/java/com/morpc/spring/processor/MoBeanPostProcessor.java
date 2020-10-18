package com.morpc.spring.processor;

import com.morpc.annotations.MoReference;
import com.morpc.annotations.MoService;
import com.morpc.spring.helper.MoRpcReferenceHelper;
import com.morpc.spring.helper.MoRpcRegisterHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * spring bean post processor
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Component
public class MoBeanPostProcessor implements BeanPostProcessor {

    /**
     * register helper
     */
    @Resource
    @Lazy
    private MoRpcRegisterHelper moRpcRegisterHelper;

    /**
     * reference helper
     */
    @Resource
    @Lazy
    private MoRpcReferenceHelper moRpcReferenceHelper;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        attachRpcReference(bean);
        return bean;
    }

    private void attachRpcReference(Object bean) throws BeansException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            MoReference reference = field.getAnnotation(MoReference.class);
            if (reference == null) {
                continue;
            }

            Class<?> interfaceType = field.getType();
            String version = reference.version();
            int timeout = reference.timeout();

            try {
                Object serviceStub = moRpcReferenceHelper.createRpcReference(
                    interfaceType, version, timeout);
                field.set(bean, serviceStub);
            } catch (Throwable t) {
                throw new FatalBeanException(String.format(
                    "Cannot attach MoReference: interface = %s", interfaceType.getName()), t);
            }
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        registerRpcService(bean);
        return bean;
    }

    private void registerRpcService(Object bean) throws BeansException {
        Class<?> beanClass = bean.getClass();

        MoService serviceDef = beanClass.getAnnotation(MoService.class);
        if (serviceDef == null) {
            return;
        }

        // fix service name
        String interfaceType = serviceDef.interfaceType();
        if (interfaceType.isEmpty()) {
            Class<?>[] interfaces = beanClass.getInterfaces();
            if (interfaces.length == 0) {
                interfaceType = beanClass.getName();
            } else {
                interfaceType = interfaces[0].getName();
            }
        }

        // register
        moRpcRegisterHelper.registerService(interfaceType, serviceDef.version(), bean);
    }
}
