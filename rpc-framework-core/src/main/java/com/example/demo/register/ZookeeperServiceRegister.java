package com.example.demo.register;

import com.example.demo.Constant.NormalConstant;
import com.example.demo.params.RpcService;
import com.example.demo.util.CuratorUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import com.example.demo.Constant.ZookeeperConstant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author RL475
 */
public class ZookeeperServiceRegister  implements ServiceRegister{

    private ZooKeeper zooKeeper;
    private CuratorUtils curatorUtils;

    public ZookeeperServiceRegister() {
//        try {
//            zooKeeper = new ZooKeeper("ryuzzzz.icu:2181", 20000, null);
////            if(zooKeeper.exists(ZookeeperConstant.ZK_ROOT_PATH,null)!=null){
////                zooKeeper.create(ZookeeperConstant.ZK_ROOT_PATH,null,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT)
////            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        curatorUtils = new CuratorUtils("ryuzzzz.icu:2181");
        if(!curatorUtils.checkExists(ZookeeperConstant.ZK_ROOT_PATH)){
            curatorUtils.createPersistent(ZookeeperConstant.ZK_ROOT_PATH,false);
        }
    }

    @Override
    public void register(List<RpcService> service) {
        try {
            checkDestroyed();
            for (RpcService rpcService : service) {
    //            String path=ZookeeperConstant.ZK_ROOT_PATH+ NormalConstant.PATH_SEPARATOR +rpcService.getRpcServiceName()+NormalConstant.PATH_SEPARATOR+rpcService.getAddress();
                String path=ZookeeperConstant.ZK_ROOT_PATH+ NormalConstant.PATH_SEPARATOR +rpcService.getRpcServiceName();
                if(!curatorUtils.checkExists(path)){
                    curatorUtils.createEphemeral(path,rpcService.getAddress(),false);
                }

            }
        } catch (Throwable e) {
        throw new RuntimeException("Failed to register " +" to zookeeper " + " cause: " + e.getMessage(), e);
    }
    }

    @Override
    public void unRegister(String RpcServiceName) {
        try {
        checkDestroyed();
        String path=ZookeeperConstant.ZK_ROOT_PATH+ NormalConstant.PATH_SEPARATOR +RpcServiceName;
        curatorUtils.deletePath(path);
    } catch (Throwable e) {
        throw new RuntimeException("Failed to unregister " + "to zookeeper " + RpcServiceName + ", cause: " + e.getMessage(), e);
    }
    }

    @Override
    public RpcService getService(String RpcServiceName) {
        return null;
    }

    private void checkDestroyed() {
        if (curatorUtils.getClient() == null) {
            throw new IllegalStateException("registry is destroyed");
        }
    }


}
