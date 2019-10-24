package com.abosen.netty.example07.grpc;

import com.abosen.netty.example07.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

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

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        int age = request.getAge();
        log.info("getStudentsByAge: [{}]", age);
        // 返回是流式的，所以可以调用多个onNext
        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(age).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(age).setCity("天津").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(age).setCity("成都").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("赵六").setAge(age).setCity("深圳").build());

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        // 通过注册回调来对流请求进行处理
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                log.info("onNext: age[{}]", value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                log.error("error message:[{}]", t.getMessage(), t);
            }

            @Override
            public void onCompleted() {
                StudentResponse response = StudentResponse.newBuilder().setName("张三").setAge(22).setCity("北京").build();
                StudentResponse response2 = StudentResponse.newBuilder().setName("李四").setAge(24).setCity("深圳").build();

                StudentResponseList list = StudentResponseList.newBuilder()
                        .addStudentResponse(response).addStudentResponse(response2).build();
                responseObserver.onNext(list);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest value) {
                log.info("stream request info:[{}]", value.getRequestInfo());
                // 同时返回一个uuid
                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                log.error("error message:[{}]", t.getMessage(), t);
            }

            @Override
            public void onCompleted() {
                // 请求结束时，关闭对方的流
                responseObserver.onCompleted();
                log.info("on completed");
            }
        };
    }
}
