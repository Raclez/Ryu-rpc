package com.example.demo.params;

import lombok.*;

import java.io.Serializable;

/**
 * @author RL475
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RpcRequest implements Serializable{
    private static final long serialVersionUID = 1905122041950251207L;

    /**
     * 调用的接口全限定名，服务端根据它找到实现
     */
    private String interfaceName;
    /**
     * 调用接口中的方法名
     */
    private String methodName;
    /**
     * 方法返回类型
     */
    private Class<?> returnType;
    /**
     * 方法参数类型数组
     */
    private Class[] parameterTypes;
    /**
     * 方法参数值数组
     */
    private Object[] parameterValue;


}