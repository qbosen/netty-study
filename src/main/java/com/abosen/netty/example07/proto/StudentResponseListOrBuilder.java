// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Student.proto

package com.abosen.netty.example07.proto;

public interface StudentResponseListOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.abosen.protobuf.StudentResponseList)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .com.abosen.protobuf.StudentResponse studentResponse = 1;</code>
   */
  java.util.List<StudentResponse>
      getStudentResponseList();
  /**
   * <code>repeated .com.abosen.protobuf.StudentResponse studentResponse = 1;</code>
   */
  StudentResponse getStudentResponse(int index);
  /**
   * <code>repeated .com.abosen.protobuf.StudentResponse studentResponse = 1;</code>
   */
  int getStudentResponseCount();
  /**
   * <code>repeated .com.abosen.protobuf.StudentResponse studentResponse = 1;</code>
   */
  java.util.List<? extends StudentResponseOrBuilder>
      getStudentResponseOrBuilderList();
  /**
   * <code>repeated .com.abosen.protobuf.StudentResponse studentResponse = 1;</code>
   */
  StudentResponseOrBuilder getStudentResponseOrBuilder(
          int index);
}