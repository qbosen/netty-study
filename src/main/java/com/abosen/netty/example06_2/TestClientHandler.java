package com.abosen.netty.example06_2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static com.abosen.netty.example06_2.DataInfo.MyMessage.DataType.*;

/**
 * @author qiubaisen
 * @date 2019-10-20
 */
public class TestClientHandler extends SimpleChannelInboundHandler<DataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ThreadLocalRandom.current().ints(10, 1, 4).forEach(it -> {
                    Optional<DataInfo.MyMessage.DataType> dataType = Optional.ofNullable(DataInfo.MyMessage.DataType.forNumber(it));
                    DataInfo.MyMessage myMessage = getMessage(dataType.orElse(PersonType));
                    ctx.channel().writeAndFlush(myMessage);
                }
        );
    }

    private DataInfo.MyMessage getMessage(DataInfo.MyMessage.DataType dataType) {

        DataInfo.MyMessage myMessage = null;
        switch (dataType) {
            case PersonType:
                myMessage = DataInfo.MyMessage.newBuilder()
                        .setDataType(PersonType)
                        .setPerson(DataInfo.Person.newBuilder().setName("张三").setAge(20).setAddress("北京").build())
                        .build();
                break;
            case DogType:
                myMessage = DataInfo.MyMessage.newBuilder()
                        .setDataType(DogType)
                        .setDog(DataInfo.Dog.newBuilder().setName("一只狗").setAge(20).build())
                        .build();
                break;
            case CatType:
                myMessage = DataInfo.MyMessage.newBuilder()
                        .setDataType(CatType)
                        .setCat(DataInfo.Cat.newBuilder().setName("一只猫").setCity("上海").build())
                        .build();
                break;
            default:
        }
        return myMessage;
    }
}
