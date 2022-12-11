package com.example.demo.util;

public class EntityResponse<T> {

  private T entity;
  private int code;

  private String message;

  private String token;

  public EntityResponse(T entity, CustomStatus customStatus, String token) {
    this.code = customStatus.getCode();
    this.message = customStatus.getMessage();
    this.entity = entity;
    this.token = token;

  }

}
