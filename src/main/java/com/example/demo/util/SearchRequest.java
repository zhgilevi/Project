package com.example.demo.util;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequest {
  private String username;

  private String token;

  public SearchRequest(String username, String token) {
    this.username = username;
    this.token = token;
  }
}
