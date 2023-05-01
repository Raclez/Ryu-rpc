package com.example.demo.aspect;

import com.example.demo.annotation.RpcService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author RL475
 */
//@Aspect
//@Component
public class RpcServiceAspect {
    @Pointcut(value = "@annotation(rpcService)")
    public void pointcut(RpcService rpcService){

    }
    @Before(value = "pointcut(rpcService)")
    public void  before(ProceedingJoinPoint point, RpcService rpcService){
        System.out.println("---------------------------------------");
        String name = point.getSignature().getName();
        System.out.println(name);
    }


}
