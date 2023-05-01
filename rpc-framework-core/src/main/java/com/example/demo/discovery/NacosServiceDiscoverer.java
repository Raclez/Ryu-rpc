package com.example.demo.discovery;

import com.example.demo.params.RpcService;

import java.util.List;

/**
 * @author ryu
 */
public class NacosServiceDiscoverer implements ServiceDiscoverer{

    @Override
    public List<RpcService> getService(String name) {
        return null;
    }
}
