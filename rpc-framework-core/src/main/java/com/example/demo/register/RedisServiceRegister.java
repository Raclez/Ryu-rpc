package com.example.demo.register;

import com.example.demo.Constant.ZookeeperConstant;
import com.example.demo.params.RpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ryu
 */
public class RedisServiceRegister implements ServiceRegister{
    @Autowired
    StringRedisTemplate redisTemplate;


    @Override
    public void register(List<RpcService> service) {
        for (RpcService rpcService : service) {
            redisTemplate.opsForHash().put(ZookeeperConstant.ZK_ROOT_PATH+"/"+rpcService.getRpcServiceName(),rpcService.getAddress(),"1000");
//            redisTemplate.expire(ZookeeperConstant.ZK_ROOT_PATH+"/"+rpcService.getRpcServiceName(),1000, TimeUnit.SECONDS);

        }

    }

    @Override
    public void unRegister(String RpcServiceName) {

    }

    @Override
    public  RpcService getService(String RpcServiceName){
        return null;
    }
}
