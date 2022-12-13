package com.example.demo.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
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


  @ElementCollection
  private long[] participant;

  @ElementCollection
  private ArrayList<Long> messages;

  public Chat(long[] participant) {
    this.participant = participant;
    this.messages = new ArrayList<Long>();
  }


}
