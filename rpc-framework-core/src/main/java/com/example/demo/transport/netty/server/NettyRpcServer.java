package com.example.demo.transport.netty.server;

import com.example.demo.transport.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import com.example.demo.transport.netty.codec.NettyRpcDecoder;
import com.example.demo.transport.netty.codec.NettyRpcEncoder;
import com.example.demo.transport.netty.handler.NettyServerHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author RL475
 */

public class NettyRpcServer implements RpcServer {

@Override
    public void  start(){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup(4);
        try {
            ChannelFuture channelFuture = new ServerBootstrap().childOption(ChannelOption.SO_KEEPALIVE,true).
                    option(ChannelOption.SO_BACKLOG,128)
                    .group(boss, work).channel(NioServerSocketChannel.class).
                    childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            socketChannel.pipeline().addLast(new IdleStateHandler(30,0,0, TimeUnit.SECONDS));
                            socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
                            socketChannel.pipeline().addLast(new NettyRpcDecoder());
                            socketChannel.pipeline().addLast(new NettyRpcEncoder());
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }


                    }).bind(8080).sync();
            channelFuture.channel().closeFuture().addListener(future -> {
                if(future.isSuccess()){
                    boss.shutdownGracefully();
                    work.shutdownGracefully();
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
