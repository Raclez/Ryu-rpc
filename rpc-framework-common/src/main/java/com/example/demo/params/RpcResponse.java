package com.example.demo.params;

import lombok.*;

import java.io.Serializable;

/**
 * @author RL475
 */
@Data
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = 715745410605631233L;


    /**
     * response message
     */
    private String message;
    /**
     * response body
     */
    private Object data;



}
