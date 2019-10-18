package com.abosen.netty.example02;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author qiubaisen
 * @date 2019-10-19
 */

@Slf4j
public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("server addr:{}", ctx.channel().remoteAddress());
        log.info("receive msg:{}", msg);
        ctx.channel().writeAndFlush("from client: " + LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("客户端建立连接后立刻发送一条数据");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("caught exception", cause);
        ctx.close();
    }
}
