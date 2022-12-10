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
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long fParticipant;
  private long sParticipant;

  public Chat(long fParticipant, long sParticipant) {
    this.fParticipant = fParticipant;
    this.sParticipant = sParticipant;
  }


}
