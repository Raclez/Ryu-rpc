package com.example.demo.serialize.hessian;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.example.demo.em.SerialType;
import com.example.demo.exception.SerializableException;
import com.example.demo.serialize.Serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author RL475
 */
public class HessianSerializable implements Serializable {
    @Override
    public <T> byte[] serialize(T object) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            HessianOutput hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(object);

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new SerializableException("序列化失败");
        }

    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            HessianInput hessianInput = new HessianInput(byteArrayInputStream);
            Object o = hessianInput.readObject();

            return clazz.cast(o);

        } catch (Exception e) {
            throw new SerializableException("反序列化失败");
        }

    }

    @Override
    public byte getType() {
        return SerialType.HESSIAN_SERIAL.getCode();
    }
}
