package com.abosen.netty.example04;

import com.abosen.netty.example03.MyChatClient;

import java.io.IOException;

/**
 * @author qiubaisen
 * @date 2019-10-19
 */
public class MyClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        // reuse client from example03
        MyChatClient.main(args);
    }
}
