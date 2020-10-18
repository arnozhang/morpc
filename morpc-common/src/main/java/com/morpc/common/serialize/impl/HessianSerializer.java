package com.morpc.common.serialize.impl;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.morpc.common.serialize.MoSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * serializer implemented by hessian
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
public class HessianSerializer implements MoSerializer {

    public byte[] serialize(Object object) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        HessianOutput output = new HessianOutput(stream);
        output.writeObject(object);
        output.flush();
        output.close();

        return stream.toByteArray();
    }

    public Object deSerialize(byte[] bytes) throws Exception {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

        HessianInput input = new HessianInput(stream);
        Object object = input.readObject();
        input.close();

        return object;
    }
}
