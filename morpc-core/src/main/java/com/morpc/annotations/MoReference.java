package com.morpc.annotations;

import java.lang.annotation.*;

/**
 * rpc reference
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MoReference {

    /**
     * rpc version
     */
    String version() default "1.0";

    /**
     * rpc invoke timeout
     */
    int timeout() default 3000;
}
