package com.abosen.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiubaisen
 * @date 2019-10-19
 */

@Slf4j
public class ProtoBufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder().setName("张三").setAge(20).setAddress("北京").build();

        byte[] student2ByteArray = student.toByteArray();
        DataInfo.Student newStudent = DataInfo.Student.parseFrom(student2ByteArray);

        log.info("名字:{}，年龄:{}，地址:{}", newStudent.getName(), newStudent.getAge(), newStudent.getAddress());
    }
}
