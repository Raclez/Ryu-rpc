package com.example.demo.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author RL475
 */
@Data
@AllArgsConstructor
public class Header implements Serializable {
    /**
     * 魔数 4个字节
     */
    private int magic;

    /**
     * 版本号  2个字节
     */
    private short version;

    /**
     * 序列化类型 1个字节
     */
    private byte serialType;

    /**
     * 消息类型 1个字节
     */
    private byte reqType;

    /**
     * 请求ID 4个字节
     */
    private int requestId;
    /**
     * 消息体长度 4个字节
     */
    private int length;


}
