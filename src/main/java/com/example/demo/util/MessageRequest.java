package com.example.demo.util;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequest {

  private Long chatId;
  private String token;

  private String messageText;

  public MessageRequest(Long chatId, String token, String messageText) {
    this.chatId = chatId;
    this.token = token;
    this.messageText = messageText;
  }
}
