package org.example.zzz;

import com.example.demo.proxy.ClientProxyFactory;

public class userController {

    public  void t(){
        ClientProxyFactory clientProxyFactory = new ClientProxyFactory();
//        hello proxy = clientProxyFactory.getProxy(hello.class);
//
//          proxy.sayHello();
        user proxy1 = clientProxyFactory.getProxy(user.class);
        String to = proxy1.to();

        System.out.println(to);
        System.out.println("ggggggggg");
    }

}
