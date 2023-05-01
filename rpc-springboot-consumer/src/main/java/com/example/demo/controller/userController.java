package com.example.demo.controller;

import com.example.demo.annotation.RpcReference;
import org.example.zzz.user;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RL475
 */

@RestController
public class userController {
    @RpcReference
    user user;
    @RequestMapping("/test")
    public String t(){
        System.out.println(user.to());
        System.out.println("@@@@");
        return "成功";
    }
    @RequestMapping("/user")
    public String get(){
        return user.getUser();
    }
}
