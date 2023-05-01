package com.example.demo.transport;

import com.example.demo.annotation.RpcSpi;
import com.example.demo.params.RpcRequest;
import com.example.demo.protocol.RpcProtocol;

/**
 * @author RL475
 */
@RpcSpi
public interface RpcClient {


    /**
     * @param rpcProtocol
     *
     * @return
     */
   Object sendRequest(RpcProtocol<RpcRequest> rpcProtocol) ;
}
