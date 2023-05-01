package com.example.demo;


import com.example.demo.annotation.RpcScan;
import com.example.demo.sping.AutoConfiguration;
import com.example.demo.sping.RpcProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@RpcScan( basePackage = "com.example")
public class RpcSpringbootServerApplication {

    public static void main(String[] args) {
      SpringApplication.run(RpcSpringbootServerApplication.class, args);

    }

}
