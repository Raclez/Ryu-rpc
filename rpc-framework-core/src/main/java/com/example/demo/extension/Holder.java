package com.example.demo.extension;

/**
 * @author RL475
 */
public class Holder <T>{

    private volatile T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
