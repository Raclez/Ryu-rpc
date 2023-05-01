package com.example.demo.service;

import com.example.demo.annotation.RpcService;
import org.example.zzz.user;


/**
 * @author RL475
 */

@RpcService
public class userServiceImpl implements user {


    @Override
    public String to() {
        System.out.println("欢迎访问to");
        return "zzzzzzzgggggg";
    }

    @Override
    public String getUser() {
        return "ryuzzzzzz";
    }
}
