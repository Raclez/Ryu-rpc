package com.example.demo.register;

import com.example.demo.annotation.RpcSpi;
import com.example.demo.params.RpcService;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author RL475
 */
@RpcSpi
public interface ServiceRegister {



    void register(List<RpcService> service);

    void unRegister(String RpcServiceName);
    /**
     * @param RpcServiceName
     * @return RpcService
     */
    RpcService getService(String RpcServiceName);


}
