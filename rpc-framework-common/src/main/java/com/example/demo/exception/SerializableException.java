package com.example.demo.exception;

/**
 * 序列化异常
 * @author RL475
 */
public class SerializableException extends RuntimeException{
    private static final long serialVersionUID = 42L;

    public SerializableException() {
    }

    public SerializableException(String message) {
        super(message);
    }

    public SerializableException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializableException(Throwable cause) {
        super(cause);
    }

    public SerializableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
