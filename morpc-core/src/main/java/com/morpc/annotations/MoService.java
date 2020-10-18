package com.morpc.annotations;

import java.lang.annotation.*;

/**
 * rpc service
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MoService {

    /**
     * rpc interfaceType. default use object interface type
     */
    String interfaceType() default "";

    /**
     * rpc version
     */
    String version() default "1.0";
}
