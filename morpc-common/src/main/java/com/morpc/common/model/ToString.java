package com.morpc.common.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class ToString implements Serializable {

    private static final long serialVersionUID = 2260108801406871984L;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
