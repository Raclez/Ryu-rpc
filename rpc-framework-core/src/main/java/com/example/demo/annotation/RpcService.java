package com.example.demo.annotation;


import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * RPC service annotation, marked on the service implementation class
 *
 * @author RL475
 */
//@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Component
public @interface RpcService {

    /**
     * Service version, default value is empty string
     */
    String version() default "1";



}
