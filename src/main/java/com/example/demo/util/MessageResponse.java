package com.example.demo.util;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
  private Long id;
  private String messageText;

  public MessageResponse(Long id, String messageText) {
    this.id = id;
    this.messageText = messageText;
  }
}
