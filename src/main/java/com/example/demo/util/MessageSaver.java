package com.example.demo.util;

import com.example.demo.service.ChatMessageService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(value = "com.example.demo.service")
public class MessageSaver {

  @Autowired
  private ChatMessageService messageService;

  public void save(Map<String, String> input){
    messageService.save(input);
  }




}
