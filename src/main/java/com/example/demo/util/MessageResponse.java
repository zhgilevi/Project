package com.example.demo.util;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
  private Long id;
  private String sender;
  private String messageText;

  public MessageResponse(Long id, String sender, String messageText) {
    this.id = id;
    this.sender = sender;
    this.messageText = messageText;
  }
}
