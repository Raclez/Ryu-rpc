package com.example.demo.transport.netty.codec;

import com.example.demo.protocol.Header;
import com.example.demo.protocol.RpcProtocol;
import com.example.demo.serialize.Serializable;
import com.example.demo.serialize.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author RL475
 * 编码器
 */
@Slf4j

public class NettyRpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcProtocol<Object> objectRpcProtocol, ByteBuf byteBuf) throws Exception {
        log.info("=====================begin  encoder=====================");
        Header header = objectRpcProtocol.getHeader();
        byteBuf.writeInt(header.getMagic());
        byteBuf.writeShort(header.getVersion());
        byteBuf.writeByte(header.getSerialType());
        byteBuf.writeByte(header.getReqType());
        byteBuf.writeInt(header.getRequestId());
        Serializable serializable = SerializerManager.getSerializable(header.getSerialType());
        if(objectRpcProtocol.getContent()!=null){
            byte[] data = serializable.serialize(objectRpcProtocol.getContent());
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
        log.info("=====================finish  encoder=====================");


    }

}
