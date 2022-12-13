package com.example.demo.util;


import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
  private int code;
  Map<String, String> data;
  public LoginResponse(int code){
    this.code = code;
  }

  public LoginResponse(int code, Map data){
    this.code = code;
    this.data = data;
  }

}
