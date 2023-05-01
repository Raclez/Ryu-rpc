package com.example.demo.transport.netty.client;

import com.example.demo.params.RpcRequest;
import com.example.demo.protocol.RpcProtocol;
import com.example.demo.transport.netty.codec.NettyRpcDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import com.example.demo.transport.RpcClient;
import com.example.demo.transport.netty.codec.NettyRpcEncoder;
import com.example.demo.transport.netty.handler.NettyClientHandler;
import lombok.extern.java.Log;

import java.util.concurrent.TimeUnit;

/**
 * @author RL475
 */
public class NettyRpcClient implements RpcClient {
    private Bootstrap bootstrap;
    private NettyClientHandler nettyClientHandler;
    private NettyRpcDecoder nettyRpcDecoder;
    private NettyRpcEncoder nettyRpcEncoder;
    private LoggingHandler loggingHandler;
    private IdleStateHandler idleStateHandler;
    private NioEventLoopGroup group;

    public NettyRpcClient() {
        group = new NioEventLoopGroup();
        nettyClientHandler = new NettyClientHandler();
//        nettyRpcDecoder = new NettyRpcDecoder();
//        nettyRpcEncoder = new NettyRpcEncoder();
        loggingHandler = new LoggingHandler();



        bootstrap = new Bootstrap().group(group).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
//                socketChannel.pipeline().addLast(loggingHandler);
                socketChannel.pipeline().addLast( new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));
                socketChannel.pipeline().addLast(new NettyRpcDecoder());
                socketChannel.pipeline().addLast(new NettyRpcEncoder());
                socketChannel.pipeline().addLast(nettyClientHandler);


            }
        });
    }

    @Override
    public Object sendRequest(RpcProtocol<RpcRequest> rpcProtocol) {

        ChannelFuture future;
        Object data;
            future = bootstrap.connect("localhost", 8080).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        channelFuture.channel().writeAndFlush(rpcProtocol);
                    }else {

                    }

                }
            });

            data = nettyClientHandler.getData();

            future.channel().closeFuture().addListener(future1 ->{
                if(future1.isSuccess())
                group.shutdownGracefully();
                    });

            return data;

    }


}
