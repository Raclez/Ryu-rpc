package org.example.zzz;

public class helloController {
    private hello hello;

    public helloController(org.example.zzz.hello hello) {
        this.hello = hello;
    }

    public void test(){
        String s = hello.sayHello();
        System.out.println("------------------------------------------");
        String t = hello.sayHello();
    }
}
