package com.example.demo.proxy;

import com.example.demo.factory.SingletonFactory;
import com.example.demo.params.RpcRequest;
import com.example.demo.params.RpcResponse;
import com.example.demo.protocol.Header;
import com.example.demo.protocol.RpcProtocol;
import com.example.demo.transport.RpcClient;
import com.example.demo.transport.netty.client.NettyRpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author RL475
 */

public class ClientProxyFactory implements InvocationHandler{
    @Autowired
    private RpcClient client;
    private Object target;


    public <T> T getProxy(Class<T> clazz){
         return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
     }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            RpcProtocol<RpcRequest> rpcRequestRpcProtocol = new RpcProtocol<>();
            RpcRequest rpcRequest = new RpcRequest();
            rpcRequest.setMethodName(method.getName());
            rpcRequest.setInterfaceName(method.getDeclaringClass().getName());
            rpcRequest.setParameterTypes(method.getParameterTypes());
            rpcRequest.setParameterValue(args);
            rpcRequest.setReturnType(method.getReturnType());
            Header header = new Header(1998, (short) 1, (byte) 3, (byte) 1, 45, 0);
            rpcRequestRpcProtocol.setContent(rpcRequest);
            rpcRequestRpcProtocol.setHeader(header);
           Object bytes = client.sendRequest(rpcRequestRpcProtocol);
        RpcProtocol<RpcResponse> bytes1 = (RpcProtocol<RpcResponse>) bytes;
        Object data = bytes1.getContent().getData();
        return data;
    }
}
