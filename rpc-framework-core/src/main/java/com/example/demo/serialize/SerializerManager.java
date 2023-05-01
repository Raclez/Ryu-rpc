package com.example.demo.serialize;

import com.example.demo.serialize.hessian.HessianSerializable;
import com.example.demo.serialize.kryo.KryoSerializable;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author RL475
 */
public class SerializerManager {
    final static ConcurrentHashMap<Byte, Serializable> hashMap = new ConcurrentHashMap<Byte, Serializable>();


    static {
        HessianSerializable hessianSerializable = new HessianSerializable();
        KryoSerializable kryoSerializable  = new KryoSerializable();
        hashMap.put(hessianSerializable.getType(), hessianSerializable);
        hashMap.put(kryoSerializable.getType(), kryoSerializable);

    }

    public static Serializable getSerializable(byte key) {
        Serializable serializable = hashMap.get(key);
        if (serializable == null) {
            return new KryoSerializable();
        }
        return serializable;
    }

}
