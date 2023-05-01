package com.example.demo.discovery;

import com.example.demo.Constant.ZookeeperConstant;
import org.apache.zookeeper.*;
import com.example.demo.params.RpcService;

import java.io.IOException;
import java.util.List;

/**
 * @author RL475
 */
public class ZookeeperServiceDiscoverer implements ServiceDiscoverer{
private ZooKeeper zooKeeper;
    public ZookeeperServiceDiscoverer() {
        try {
            zooKeeper = new ZooKeeper("ryuzzzz.icu:2181", 20000, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RpcService> getService(String name) {
return null;
    }
    public boolean exist(String name){
        try {
            if(zooKeeper.exists(ZookeeperConstant.ZK_ROOT_PATH+name,null)==null){
                return false;
            }
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return true;
    }



}
