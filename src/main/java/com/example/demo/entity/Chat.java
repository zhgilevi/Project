package com.example.demo.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;
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


  private Long fParticipant;

  private Long sParticipant;

  @ElementCollection
  private List<Long> messages;

  public Chat(Long fParticipant, Long sParticipant) {
    this.fParticipant =fParticipant;
    this.sParticipant = sParticipant;
    this.messages = new ArrayList<>();
  }


}
