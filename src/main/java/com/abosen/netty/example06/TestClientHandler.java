package com.abosen.netty.example06;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author qiubaisen
 * @date 2019-10-20
 */
public class TestClientHandler extends SimpleChannelInboundHandler<DataInfo.Person> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Person msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DataInfo.Person person = DataInfo.Person.newBuilder().setName("张三").setAge(20).setAddress("北京").build();
        ctx.channel().writeAndFlush(person);
    }
}
