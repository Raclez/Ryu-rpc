package com.example.demo.annotation;


import java.lang.annotation.*;

/**
 * RPC reference annotation, autowire the service implementation class
 *
 * @author RL475
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {

    /**
     * Service version, default value is empty string
     */
    String version() default "1";

    /**
     * Service group, default value is empty string
     */
    String group() default "";

}
