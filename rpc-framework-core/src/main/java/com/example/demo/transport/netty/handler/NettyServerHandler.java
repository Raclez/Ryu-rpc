package com.example.demo.transport.netty.handler;

import com.example.demo.factory.SingletonFactory;
import com.example.demo.params.RpcRequest;
import com.example.demo.params.RpcResponse;
import com.example.demo.protocol.RpcProtocol;
import com.example.demo.provider.ServiceProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * @author RL475
 */

public class NettyServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {
    private ServiceProvider serviceProvider;
    public  NettyServerHandler(){
        serviceProvider= SingletonFactory.getInstance(ServiceProvider.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcRequest> rpcRequestRpcProtocol) throws Exception {
//                log.debug("server receives message :{}",rpcRequestRpcProtocol);
//        RpcProtocol<RpcResponse> responseRpcProtocol = new RpcProtocol<>();
//        Header header = responseRpcProtocol.getHeader();
//        System.out.println(rpcRequestRpcProtocol);
//Class.forName(rpcRequestRpcProtocol.getContent().getInterfaceName().
        Object aClass = serviceProvider.getService(rpcRequestRpcProtocol.getContent().getInterfaceName());
        Method method = aClass.getClass().getMethod(rpcRequestRpcProtocol.getContent().getMethodName(), rpcRequestRpcProtocol.getContent().getParameterTypes());
        Object result = method.invoke(aClass, rpcRequestRpcProtocol.getContent().getParameterValue());
        RpcProtocol<Object> protocol = new RpcProtocol<>();
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setData(result);
        protocol.setContent(rpcResponse);
        rpcRequestRpcProtocol.getHeader().setReqType((byte) 2);
        protocol.setHeader(rpcRequestRpcProtocol.getHeader());
        channelHandlerContext.writeAndFlush(protocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.close();
    }
}
