package com.example.demo.register;


import com.example.demo.params.RpcService;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author ryu
 */
public class NacosServiceRegister implements ServiceRegister{


    @Override
    public void register(List<RpcService> service) {

    }

    @Override
    public void unRegister(String RpcServiceName) {

    }

    @Override
    public RpcService getService(String RpcServiceName) {
        return null;
    }
}
