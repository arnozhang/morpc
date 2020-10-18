package com.morpc.demo.service.declare.hello;

import com.morpc.common.model.ToString;
import lombok.Getter;
import lombok.Setter;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Getter
@Setter
public class HelloRequest extends ToString {

    private static final long serialVersionUID = -933792337969634378L;

    private String message;
}
