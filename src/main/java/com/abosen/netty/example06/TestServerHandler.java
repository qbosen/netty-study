package com.abosen.netty.example06;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiubaisen
 * @date 2019-10-20
 */
@Slf4j
public class TestServerHandler extends SimpleChannelInboundHandler<DataInfo.Person> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Person msg) throws Exception {
        log.info("名字:{}，年龄:{}，地址:{}", msg.getName(), msg.getAge(), msg.getAddress());
    }
}
