package com.example.demo;

import com.example.demo.sping.AutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class RpcSpringbootConsumerApplication {

    public static void main(String[] args) {

        SpringApplication.run(RpcSpringbootConsumerApplication.class, args);
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AutoConfiguration.class);
        Object bean = annotationConfigApplicationContext.getBean("serializable");
        System.out.println(bean.getClass().getCanonicalName().toString());
    }

}
