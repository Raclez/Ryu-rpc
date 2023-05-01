package com.example.demo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 实现扩展机制
 * @author RL475
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)

public @interface RpcSpi {
//    String value() default "";
}
