package com.abosen.netty.example03;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiubaisen
 * @date 2019-10-19
 */
@Slf4j
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 用于存储所有连接的channel对象
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        // 收到客户端a的消息，发送给所有客户端
        channelGroup.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush(String.format("[%s] 发送消息：%s\n", channel.remoteAddress(), msg));
            } else {
                ch.writeAndFlush(String.format("[自己] 发送消息：%s\n", msg));
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 连接建立的时候 广播连接消息，并加入group
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(String.format("[服务器] - %s 加入\n", channel.remoteAddress()));
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 连接断开，广播离开消息
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(String.format("[服务器] - %s 离开\n", channel.remoteAddress()));
        // netty在断开的时候会自动调用删除
        boolean removed = channelGroup.remove(channel);
        log.info("### 断开连接时，[{}]手动调用从group中删除channel", removed ? "需要" : "不需要");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("client [{}] 上线了", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("client [{}] 下线了", ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("chat server exception caught!", cause);
        ctx.close();
    }
}
