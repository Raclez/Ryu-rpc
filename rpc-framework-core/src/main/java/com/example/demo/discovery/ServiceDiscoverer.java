package com.example.demo.discovery;

import com.example.demo.annotation.RpcSpi;
import com.example.demo.params.RpcService;

import java.util.List;

/**
 * @author RL475
 */
@RpcSpi
public interface ServiceDiscoverer {
    /**
     * @param name
     * @return
     */
    List<RpcService> getService(String name);

}
