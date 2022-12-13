package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IdList {

  public ArrayList<String> participants ;

  public IdList(ArrayList<String> participants) {
    this.participants = participants;
    System.out.println("asdasdasdasdasd");
  }
}
