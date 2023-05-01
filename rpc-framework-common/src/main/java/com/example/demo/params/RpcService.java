package com.example.demo.params;

import lombok.Builder;
import lombok.Data;

/**
 * @author RL475
 */
@Data
@Builder
public class RpcService {

    private Object service;
    private  String version;

    private String host;
    private  String port;
    public String getAddress(){
        return this.host+":"+this.port;
    }
   public String getRpcServiceName(){
        return this.getServiceName();
    }

    public String getServiceName() {

        return service.getClass().getInterfaces()[0].getCanonicalName();
    }
}
