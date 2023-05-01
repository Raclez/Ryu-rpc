package com.example.demo.serialize.protostuff;

import com.example.demo.em.SerialType;
import com.example.demo.serialize.Serializable;

/**
 * @author RL475
 */
public class ProtostuffSerializable implements Serializable {
    @Override
    public <T> byte[] serialize(T object) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return null;
    }

    @Override
    public byte getType() {
        return SerialType.PROTOSTUFF_SERIAL.getCode();
    }
}
