package com.example.demo.util;

import com.example.demo.service.ChatMessageService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSaver {

  @Autowired
  private ChatMessageService messageService;

  public void save(Map<String, String> input){
    messageService.save(input);
  }




}
