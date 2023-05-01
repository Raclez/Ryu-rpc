package com.example.demo.sping;

import com.example.demo.annotation.RpcReference;
import com.example.demo.factory.SingletonFactory;
import com.example.demo.provider.ServiceProvider;
import com.example.demo.proxy.ClientProxyFactory;
import com.example.demo.register.ServiceRegister;
import com.example.demo.transport.RpcServer;
import com.example.demo.transport.netty.server.NettyRpcServer;
import lombok.extern.slf4j.Slf4j;
import com.example.demo.annotation.RpcService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * @author RL475
 */

@Slf4j
public class RpcProcessor implements ApplicationListener<ContextRefreshedEvent>, DisposableBean {
    @Autowired
    private ServiceProvider serviceProvider;
@Autowired
    private RpcServer Server;

@Resource
Environment environment;
@Autowired
    private ClientProxyFactory clientProxyFactory;
    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        if(Objects.isNull(applicationContext.getParent())){
        injectRpcService(applicationContext);
        injectRpcReference(applicationContext);
        }

    }

    private void injectRpcReference(ApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Class<?> clazz = applicationContext.getType(beanName);
            if (Objects.isNull(clazz)){
                continue;
            }
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                RpcReference reference = field.getAnnotation(RpcReference.class);
                if (Objects.isNull(reference)){
                    continue;
                }
                Class<?> fieldClass = field.getType();
                Object bean = applicationContext.getBean(beanName);
                field.setAccessible(true);
                String version = reference.version();
//             clientProxyFactory = new ClientProxyFactory();
                try {
                    field.set(bean, clientProxyFactory.getProxy(fieldClass));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void injectRpcService(ApplicationContext applicationContext){
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(RpcService.class);
        if(map.size()!=0){
            for (Object value : map.values()) {
                ArrayList<com.example.demo.params.RpcService> list = new ArrayList<>();
                Class<?> aClass = value.getClass();
                RpcService service = aClass.getAnnotation(RpcService.class);
                String version = service.version();
                Class<?>[] interfaces = aClass.getInterfaces();
                String hostAddress;
                try {
                  hostAddress = Inet4Address.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
                String port = environment.getProperty("server.port");

                if(interfaces.length>1){
                    for (Class<?> anInterface : interfaces) {
                        String name = anInterface.getName();
                        if(StringUtils.hasLength(version)){
                            com.example.demo.params.RpcService service1 = com.example.demo.params.RpcService.builder().version(version).service(value).host(hostAddress).port(port).build();
                            list.add(service1);
                        }

                    }
                }else {
                    Class<?> anInterface = interfaces[0];
                    String name = anInterface.getName();
                    if(StringUtils.hasLength(version)){
                        com.example.demo.params.RpcService service1 = com.example.demo.params.RpcService.builder().version(version).service(value).host(hostAddress).port(port).build();
                        list.add(service1);
                    }
                }
//                serviceProvider = SingletonFactory.getInstance(ServiceProvider.class);
                serviceProvider.publishService(list);
            }
            Server.start();
        }

    }
}
