package com.example.demo.sping;

import com.example.demo.annotation.RpcService;
import com.example.demo.factory.SingletonFactory;
import com.example.demo.provider.ServiceProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author RL475
 */

//@Component
public class RpcBeanPostProcessor implements BeanPostProcessor {
 private ServiceProvider serviceProvider;

    public RpcBeanPostProcessor() {
        serviceProvider= SingletonFactory.getInstance(ServiceProvider.class);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if(bean.getClass().isAnnotationPresent(RpcService.class)){
                RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
                System.out.println("{      }   :"+rpcService);
                com.example.demo.params.RpcService rpcService1 = com.example.demo.params.RpcService.builder().version(rpcService.version()).service(bean).build();
//                serviceProvider.publishService(rpcService1);
            }
            return bean;
    }
}
