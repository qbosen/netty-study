package com.abosen.netty.example07.grpc;

import com.abosen.netty.example07.proto.MyRequest;
import com.abosen.netty.example07.proto.MyResponse;
import com.abosen.netty.example07.proto.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiubaisen
 * @date 2019-10-22
 */

@Slf4j
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        log.info("接收到客户端信息：[{}]", request.getUsername());
        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());
        responseObserver.onCompleted();
    }
}
