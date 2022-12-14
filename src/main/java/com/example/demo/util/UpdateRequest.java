package com.example.demo.util;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {

  private Long id;
  private String username;
  private String fName;
  private String lName;
  private String password;

  public UpdateRequest(String username, String fName, String lName, String password, Long id) {
    this.username = username;
    this.fName = fName;
    this.lName = lName;
    this.password = password;
    this.id = id;

  }
}
