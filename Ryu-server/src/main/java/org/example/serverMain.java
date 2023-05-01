package org.example;

import com.example.demo.annotation.RpcScan;
import com.example.demo.params.RpcService;
import com.example.demo.transport.netty.server.NettyRpcServer;
import org.example.zzz.helloIml;
import org.example.zzz.userIml;

@RpcScan(basePackage = "org.example")
public class serverMain {
    public static void main(String[] args) {
        helloIml helloIml = new helloIml();
        userIml userIml = new userIml();
        RpcService service1 = RpcService.builder().service(userIml).version("1").build();
        RpcService service = RpcService.builder().service(helloIml).version("1").build();
        NettyRpcServer nettyRpcServer = new NettyRpcServer();
//               nettyRpcServer .addService(service);
//               nettyRpcServer.addService(service1);
               nettyRpcServer.start();



    }
}