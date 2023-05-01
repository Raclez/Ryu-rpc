package com.example.demo.transport.netty.handler;

import com.example.demo.params.RpcResponse;
import com.example.demo.protocol.RpcProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.CountDownLatch;

/**
 * @author RL475
 */
@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {
//    private  RpcProtocol<RpcRequest> data;
//    public NettyClientHandler(RpcProtocol<RpcRequest> data) {
//        this.data=data;
//    }
private CountDownLatch countDownLatch;
private Object data=null;
public NettyClientHandler (){
   countDownLatch = new CountDownLatch(1);
}
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(data);
////        log.debug("client send message:{}",data);
//        ctx.writeAndFlush((RpcProtocol<RpcRequest>)data);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcResponse> rpcResponseRpcProtocol) throws Exception {
            data=rpcResponseRpcProtocol;
        countDownLatch.countDown();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

    public Object getData(){
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return data;

    }


}
