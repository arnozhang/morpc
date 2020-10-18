package com.morpc.spring.parser.config;

import com.morpc.constants.MoRpcConstants;
import com.morpc.spring.helper.MoRpcRegisterHelper;
import lombok.Getter;
import lombok.Setter;

/**
 * <morpc:service ref="serviceImplBean" interface="com.xx.xx"/>
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class MoRpcServiceConfigBean extends MoRpcBaseConfigBean {

    /**
     * rpc service bean ref
     */
    private String ref;

    /**
     * rpc service name
     */
    private String interfaceType;

    @Override
    public void afterPropertiesSet() throws Exception {
        // get service bean
        Object service = applicationContext.getBean(ref);

        // register service
        MoRpcRegisterHelper moRpcRegisterHelper = applicationContext.getBean(
            "moRpcRegisterHelper", MoRpcRegisterHelper.class);
        moRpcRegisterHelper.registerService(interfaceType, MoRpcConstants.CURRENT_RPC_VERSION, service);
    }
}
