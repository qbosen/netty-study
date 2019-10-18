package com.abosen.netty.example02;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author qiubaisen
 * @date 2019-10-18
 */

@Slf4j
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("remote addr:{}, msg:{}", ctx.channel().remoteAddress(), msg);

        ctx.channel().writeAndFlush("from server: " + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("caught exception!", cause);
        ctx.close();
    }
}
