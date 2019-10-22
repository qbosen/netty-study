package com.abosen.netty.example07;

import com.abosen.netty.example07.grpc.StudentServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author qiubaisen
 * @date 2019-10-22
 */
@Slf4j
public class GrpcServer {
    private Server server;

    private void start() throws IOException {
        this.server = ServerBuilder.forPort(8899).addService(new StudentServiceImpl()).build().start();
        log.info("server started");
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            log.info("close JVM");
            GrpcServer.this.stop();
        }));
        log.info("hook added");
    }
    private void stop(){
        if (server != null) {
            server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();
        server.start();
        // 防止服务器自动关闭
        server.awaitTermination();
    }

}
