package com.abosen.netty.example06_2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiubaisen
 * @date 2019-10-20
 */
@Slf4j
public class TestServerHandler extends SimpleChannelInboundHandler<DataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.MyMessage msg) throws Exception {
        DataInfo.MyMessage.DataType dataType = msg.getDataType();

        switch (dataType) {
            case PersonType:
                DataInfo.Person person = msg.getPerson();
                log.info("名字:{}，年龄:{}，地址:{}", person.getName(), person.getAge(), person.getAddress());
                break;
            case DogType:
                DataInfo.Dog dog = msg.getDog();
                log.info("名字:{}，年龄:{}", dog.getName(), dog.getAge());
                break;
            case CatType:
                DataInfo.Cat cat = msg.getCat();
                log.info("名字:{}，城市:{}", cat.getName(), cat.getCity());
                break;
            default:
        }

    }
}
