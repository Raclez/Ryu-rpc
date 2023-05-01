package com.example.demo.transport.netty.codec;

import com.example.demo.Constant.ProtocolConstant;
import com.example.demo.params.RpcRequest;
import com.example.demo.params.RpcResponse;
import com.example.demo.protocol.Header;
import com.example.demo.protocol.RpcProtocol;
import com.example.demo.serialize.Serializable;
import com.example.demo.serialize.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import com.example.demo.em.ReqType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
/**
 * @author RL475
 */

@Slf4j
public class NettyRpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        log.info("=====================begin  decoder=====================");
            if(byteBuf.readableBytes()< ProtocolConstant.HEAD_TOTAL_LEN){
                throw new Exception("消息格式不正确");
            }
        int magic = byteBuf.readInt();
            if(magic!=ProtocolConstant.MAGIC){
                throw new IllegalArgumentException("Illegal request parameter 'magic'"+magic);
            }
        short version = byteBuf.readShort();
        byte  serialType= byteBuf.readByte();
        byte reqType = byteBuf.readByte();
        int requestId = byteBuf.readInt();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes,0,length);
        Serializable serializable = SerializerManager.getSerializable(serialType);
        Header header = new Header(magic, version, serialType, reqType, requestId, length);
        ReqType type = ReqType.findByCode(reqType);
        switch (type){
            case REQUEST:
                RpcRequest request=null;
                RpcProtocol<RpcRequest> rpcProtocol = new RpcProtocol<>();
                rpcProtocol.setHeader(header);
                if(bytes!=null){
                    request= serializable.deserialize(RpcRequest.class, bytes);
                }

                rpcProtocol.setContent(request);
                list.add(rpcProtocol);
                break;
            case RESPONSE:
                RpcResponse response = serializable.deserialize(RpcResponse.class, bytes);
                RpcProtocol<RpcResponse> responseRpcProtocol = new RpcProtocol<RpcResponse>();
                responseRpcProtocol.setHeader(header);
                responseRpcProtocol.setContent(response);
                list.add(responseRpcProtocol);
                break;
            default:
                break;
        }

        log.info("=====================finish  decoder=====================");
    }
}
