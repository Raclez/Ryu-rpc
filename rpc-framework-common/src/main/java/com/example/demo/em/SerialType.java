package com.example.demo.em;

/**
 * @author RL475
 */

public enum SerialType {
    KRYO_SERIAL((byte)1),
    HESSIAN_SERIAL((byte)2),
    PROTOSTUFF_SERIAL((byte)3),
    JAVA_SERIAL((byte)4),
    JSON_SERIAL((byte)5);

    private  byte code;

    SerialType(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
