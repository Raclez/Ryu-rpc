package com.example.demo.provider;

import com.example.demo.register.ServiceRegister;
import com.example.demo.register.ZookeeperServiceRegister;
import com.example.demo.factory.SingletonFactory;
import com.example.demo.params.RpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author RL475
 */

public class ServiceProvider {

    private static ConcurrentHashMap<String,Object>hashMap=new ConcurrentHashMap<String,Object>();
    @Autowired
    private ServiceRegister register;



   public Object getService(String rpcServiceName) {

       RpcService rpcService = (RpcService) hashMap.get(rpcServiceName);
       Object service = rpcService.getService();
       if(service!=null){
           return service;
       }
       RpcService registerService = register.getService(rpcServiceName);
       if(registerService==null){
           throw new RuntimeException("no found service");
       }
       return registerService;
   }

   public void publishService(List<RpcService> rpcService) {
       ArrayList<RpcService> list = new ArrayList<>();
       for (RpcService service : rpcService) {
           String rpcServiceName = service.getRpcServiceName();
           if(hashMap.containsKey(rpcServiceName))
               return;
           hashMap.put(rpcServiceName,service);
       list.add(service);
       }
       register.register(list);
    }


}
