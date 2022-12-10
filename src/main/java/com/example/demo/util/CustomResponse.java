package com.example.demo.util;

import java.util.Collection;
import lombok.Data;

@Data
public class CustomResponse<T> {

  private int code;

  private String message;

  private String token;

  private Collection<T> responseList;

  public CustomResponse(Collection<T> response, CustomStatus customStatus, String token) {
    this.responseList = response;
    this.code = customStatus.getCode();
    this.message = customStatus.getMessage();
    this.token = token;
  }
}
