package com.example.demo.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.example.demo.em.SerialType;
import com.example.demo.exception.SerializableException;
import com.example.demo.serialize.Serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;



/**
 * @author RL475
 */
public class KryoSerializable implements Serializable {

    /**
     * Because Kryo is not thread safe. So, use ThreadLocal to store Kryo objects
     */
    private final ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {

            Kryo kryo = new Kryo();
            kryo.setReferences(true);   //支持对象循环引用（否则会栈溢出）
            kryo.setRegistrationRequired(false);    //不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）
            return kryo;
        }
    };

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = kryoLocal.get();
            // Object->byte:将对象序列化为byte数组
            kryo.writeObject(output, obj);
            kryoLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            throw new SerializableException("序列化失败");
        }
    }


    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kryoLocal.get();
            // byte->Object:从byte数组中反序列化出对对象
            Object o = kryo.readObject(input, clazz);
            kryoLocal.remove();
            return clazz.cast(o);
        } catch (Exception e) {
            throw new SerializableException("反序列化失败");
        }
    }

    @Override
    public byte getType() {
        return SerialType.KRYO_SERIAL.getCode();
    }
}
