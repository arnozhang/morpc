package com.morpc.spring.parser;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * morpc:xxx XML namespace parser
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class MoRpcNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        // morpc:service
        registerBeanDefinitionParser("service", new MoRpcServiceBeanDefinitionParser());

        // morpc:reference
        registerBeanDefinitionParser("reference", new MoRpcReferenceBeanDefinitionParser());
    }
}
