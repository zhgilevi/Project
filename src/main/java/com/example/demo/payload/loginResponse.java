package com.example.demo.payload;

import lombok.Data;

@Data
public class loginResponse {
  private long id;
  private String username;
  private String fName;
  private String lName;
  private String regDate;
  private String password;

  private String token;

}
