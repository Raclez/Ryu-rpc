package com.example.demo.protocol;

import lombok.Data;

/**
 * @author RL475
 * 自定义协议
 */
@Data
public class RpcProtocol<T> {
    private Header header;
    private T content;

    public RpcProtocol() {
    }

    public RpcProtocol(Header header, T content) {
        this.header = header;
        this.content = content;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
