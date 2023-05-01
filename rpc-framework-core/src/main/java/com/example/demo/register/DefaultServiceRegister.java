package com.example.demo.register;

import com.example.demo.extension.ExtensionLoader;
import com.example.demo.params.RpcService;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author RL475
 */
public class DefaultServiceRegister implements ServiceRegister{


    @Override
    public void register(List<RpcService> service) {

    }

    @Override
    public void unRegister(String RpcServiceName) {

    }

    @Override
    public  RpcService getService(String RpcServiceName) {
        return null;
    }


//    @Override
//    public void register(String name, InetSocketAddress address) {
//        if(StringUtil.isNullOrEmpty(name)&&StringUtil.isNullOrEmpty(address.toString())){
//            throw new IllegalArgumentException("Parameter cannot be empty");
//        }
//        hashMap.put(service.getName(),service);
//    }
//
//    @Override
//    public RpcService getService(String name) {
//                return hashMap.get(name);
//    }

}
