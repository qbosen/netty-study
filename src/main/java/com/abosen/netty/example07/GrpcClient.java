package com.abosen.netty.example07;

import com.abosen.netty.example07.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @author qiubaisen
 * @date 2019-10-22
 */

@Slf4j
public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext().build();
        StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub = StudentServiceGrpc.newBlockingStub(channel);
        log.info("--------------普通调用--------------");
        MyResponse myResponse = studentServiceBlockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
        log.info("response realname: [{}]", myResponse.getRealname());

        log.info("--------------流响应--------------");
        Iterator<StudentResponse> studentsResponse = studentServiceBlockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(24).build());

        while (studentsResponse.hasNext()) {
            StudentResponse student = studentsResponse.next();
            log.info("getStudentsByAge: name[{}], age[{}], city[{}]", student.getName(), student.getAge(), student.getCity());
        }

        log.info("--------------流请求--------------");
        // 收到服务器返回结果时的回调
        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach(
                        r -> log.info("studentResponse: name[{}],age[{}],city[{}]", r.getName(), r.getAge(), r.getCity()));
            }

            @Override
            public void onError(Throwable t) {
                log.error("on error: {}", t.getMessage(), t);
            }

            @Override
            public void onCompleted() {
                log.info("on completed");
            }
        };
        // 构造向服务器端发送数据的对象
        // 请求为流对象的，一定是异步发送
        StreamObserver<StudentRequest> studentRequestStreamObserver = StudentServiceGrpc.newStub(channel).getStudentWrapperByAges(studentResponseListStreamObserver);

        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(21).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(22).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(23).build());
        studentRequestStreamObserver.onCompleted();
        sleep(1);

        log.info("--------------流请求，流响应--------------");
        StreamObserver<StreamRequest> requestStreamObserver = StudentServiceGrpc.newStub(channel).biTalk(
                new StreamObserver<StreamResponse>() {
                    @Override
                    public void onNext(StreamResponse value) {
                        log.info("response info:[{}]", value.getResponseInfo());
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("on error: {}", t.getMessage(), t);
                    }

                    @Override
                    public void onCompleted() {
                        log.info("on completed");
                    }
                });
        for (int i = 0; i < 10; i++) {
            requestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());
            sleep(1);
        }
        requestStreamObserver.onCompleted();
        sleep(5);
    }

    private static void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            log.error("sleep interrupted",e);
        }
    }
}
