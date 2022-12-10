package com.example.demo.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
  private String username;
  private String fName;
  private String lName;
  private String password;

  public SignUpRequest(String username, String fName, String lName, String password) {
    this.username = username;
    this.fName = fName;
    this.lName = lName;
    this.password = password;
  }
}
