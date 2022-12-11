package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;
  private Long sender;
  private Long receiver;

  private Long chatId;

  public ChatMessage(String text, Long sender, Long receiver, Long chatId) {
    this.text = text;
    this.sender = sender;
    this.receiver = receiver;
    this.chatId = chatId;
  }
}
