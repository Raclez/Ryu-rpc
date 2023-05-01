package com.example.demo.sping;

import com.example.demo.discovery.ServiceDiscoverer;
import com.example.demo.discovery.ZookeeperServiceDiscoverer;
import com.example.demo.extension.ExtensionLoader;
import com.example.demo.provider.ServiceProvider;
import com.example.demo.proxy.ClientProxyFactory;
import com.example.demo.register.ServiceRegister;
import com.example.demo.register.ZookeeperServiceRegister;
import com.example.demo.serialize.Serializable;
import com.example.demo.serialize.protostuff.ProtostuffSerializable;
import com.example.demo.transport.RpcClient;
import com.example.demo.transport.RpcServer;
import com.example.demo.transport.netty.client.NettyRpcClient;
import com.example.demo.transport.netty.server.NettyRpcServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author RL475
 */

@Configuration
@ConfigurationProperties(prefix = "rpc.config")
public class AutoConfiguration {
    @Value( "${rpc.config.serializable}")
    String serializable;
    @Bean
    public RpcProcessor rpcProcessor(){
        return  new RpcProcessor();
    }

    @Bean
    public RpcClient client(){
        return new NettyRpcClient();
    }
    @Bean
    public RpcServer server(){
        return new NettyRpcServer();
    }
    @Bean
    public Serializable serializable(){
        System.out.println(serializable);
        return ExtensionLoader.getExtensionLoader(Serializable.class).getExtension("protostuff");
    }
    @Bean
    public ServiceDiscoverer discoverer(){
        return new ZookeeperServiceDiscoverer();
    }
    @Bean
    public ServiceRegister register(){return ExtensionLoader.getExtensionLoader(ServiceRegister.class).getExtension("zk");

    }
    @Bean
    public ServiceProvider serviceProvider(){
        return new ServiceProvider();
    }
    @Bean
    public ClientProxyFactory clientProxyFactory(){
        return new ClientProxyFactory();
    }

}
