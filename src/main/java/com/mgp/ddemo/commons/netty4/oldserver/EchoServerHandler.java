package com.mgp.ddemo.commons.netty4.oldserver;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wusq
 * @date 2019/4/19
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext context, Object message){
       // ByteBuf in = (ByteBuf) message;
        System.out.println("Server received:" + message.toString());
        context.write(message.toString());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) throws Exception{
        context.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause){
        cause.printStackTrace();
        context.close();
    }

}
