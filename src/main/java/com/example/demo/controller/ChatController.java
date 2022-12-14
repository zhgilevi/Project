package com.example.demo.controller;


import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.ChatMessageService;
import com.example.demo.service.ChatService;
import com.example.demo.util.MessageRequest;
import com.example.demo.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class ChatController {


  @Autowired
  private ChatService chatService;
  @Autowired
  private ChatMessageService messageService;

  @Autowired
  private AppUserRepository userRepository;



  @PostMapping("/send")//send to /app/chat response to /chat/{chatId}/queue/messages
  public void processMessage(Map<String, String> message){
    messageService.save(message);
    return;




  }







  }


