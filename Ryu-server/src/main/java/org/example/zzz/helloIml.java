package org.example.zzz;

import com.example.demo.annotation.RpcService;

@RpcService
public class helloIml  implements hello{
    @Override
    public String sayHello() {
        System.out.println("你好啊zzzzz");
        return "fdf";
    }
}
