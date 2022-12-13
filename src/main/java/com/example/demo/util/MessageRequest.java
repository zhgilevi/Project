package com.example.demo.util;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequest {

  private Long chatId;
  private String senderUsername;


  private String content;

  public MessageRequest(Long chatId, String senderUsername, String content) {
    this.chatId = chatId;
    this.senderUsername = senderUsername;
    this.content = content;
  }
}
