package com.abosen.netty.example07;

import com.abosen.netty.example07.proto.MyRequest;
import com.abosen.netty.example07.proto.MyResponse;
import com.abosen.netty.example07.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiubaisen
 * @date 2019-10-22
 */

@Slf4j
public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext().build();
        StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub = StudentServiceGrpc.newBlockingStub(channel);
        MyResponse myResponse = studentServiceBlockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
        log.info("response realname: [{}]",myResponse.getRealname());
    }
}
