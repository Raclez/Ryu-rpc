package org.example;

import org.example.zzz.userController;


public class ConsumerMain {
    public static void main(String[] args) {

//        ClientProxyFactory clientProxyFactory = new ClientProxyFactory();
////        hello proxy = clientProxyFactory.getProxy(hello.class);
////
////          proxy.sayHello();
//        user proxy1 = clientProxyFactory.getProxy(user.class);
//        proxy1.to();
//        System.out.println(proxy1);
        userController userController = new userController();
        userController.t();
//

    }
}