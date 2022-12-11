package com.example.demo.util;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequest {
  private String username;

  public SearchRequest(String username) {
    this.username = username;
  }
}
