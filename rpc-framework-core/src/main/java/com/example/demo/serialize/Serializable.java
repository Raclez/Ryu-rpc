package com.example.demo.serialize;

import com.example.demo.annotation.RpcSpi;

/**
 * @author RL475
 * 序列化器
 */
@RpcSpi
public interface Serializable {

    /**
     *  序列化方法
     * @param object
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T object);

    /**
     * 反序列化方法
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */

    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * 序列化类型
     * @return
     */
    byte getType();

}
