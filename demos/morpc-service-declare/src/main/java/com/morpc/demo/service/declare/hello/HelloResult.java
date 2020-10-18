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
public class HelloResult extends ToString {

    private static final long serialVersionUID = -3100717408550596489L;

    private String result;
}
