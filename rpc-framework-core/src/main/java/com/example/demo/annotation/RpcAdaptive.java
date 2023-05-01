package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @author RL475
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})

public @interface RpcAdaptive {
    String[] value() default {};
}
