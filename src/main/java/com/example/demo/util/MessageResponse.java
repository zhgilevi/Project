package com.example.demo.util;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
  private Long chatId;
  private String sender;
  private String content;

  public MessageResponse(Long chatId, String sender, String content) {
    this.chatId = chatId;
    this.sender = sender;
    this.content = content;
  }
}
