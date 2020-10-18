package com.morpc.spring.parser;

import com.morpc.spring.parser.config.MoRpcReferenceConfigBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * morpc:reference XML bean parser
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class MoRpcReferenceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return MoRpcReferenceConfigBean.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        builder.addPropertyValue("id", element.getAttribute("id"));
        builder.addPropertyValue("timeout", element.getAttribute("timeout"));
        builder.addPropertyValue("interfaceType", element.getAttribute("interface"));
    }
}
