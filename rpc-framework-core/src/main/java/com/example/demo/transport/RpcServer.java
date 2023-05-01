package com.example.demo.transport;

import com.example.demo.annotation.RpcSpi;

@RpcSpi
public interface RpcServer {
    public void start();
}
