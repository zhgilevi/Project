package com.example.demo.controller;


import com.example.demo.entity.ChatMessage;
import com.example.demo.service.ChatMessageService;
import com.example.demo.service.ChatService;
import com.example.demo.util.CustomResponse;
import com.example.demo.util.MessageRequest;
import com.example.demo.util.MessageResponse;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @Autowired
  private ChatService chatService;
  @Autowired
  private ChatMessageService messageService;



  @MessageMapping("/send")//send to /app/chat response to /chat/{chatId}/queue/messages
  public void processMessage(@Payload MessageRequest messageRequest){
    //token validation


    ChatMessage messageSaved = messageService.save(messageRequest);
    simpMessagingTemplate.convertAndSendToUser(
        messageRequest.getChatId().toString(),"/queue/messages",
        new MessageResponse(messageRequest.getChatId(),
            messageSaved.getText())
    );


  }


  @GetMapping("chat/{id}")
  public CustomResponse getMessages(@PathVariable String id){
    // token validation
    Long chatId = Long.parseLong(id);
    return messageService.getAllMessages(chatId);
  }

  @PostMapping("chat/{id}")
  public CustomResponse getChats(@RequestHeader HashMap<String,String> header){
    String token = header.get("token");
    return chatService.getAllChats(token);
  }



  }


