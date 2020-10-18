package com.morpc.spring.parser;

import com.morpc.spring.parser.config.MoRpcServiceConfigBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * morpc:service XML bean parser
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class MoRpcServiceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected boolean shouldGenerateId() {
        // generate id for serviceConfigBean
        return true;
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return MoRpcServiceConfigBean.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        builder.addPropertyValue("ref", element.getAttribute("ref"));
        builder.addPropertyValue("interfaceType", element.getAttribute("interface"));
    }
}
