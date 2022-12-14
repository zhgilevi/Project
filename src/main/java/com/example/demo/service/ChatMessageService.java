package com.example.demo.service;


import com.example.demo.entity.Chat;
import com.example.demo.entity.ChatMessage;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.ChatRepository;
import com.example.demo.util.CustomResponse;
import com.example.demo.util.CustomStatus;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.MessageRequest;
import com.example.demo.util.MessageResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class ChatMessageService {

  @Autowired
  ChatMessageRepository messageRepository;

  @Autowired
  JwtUtil jwtUtil;

  @Autowired
  ChatRepository chatRepository;

  @Autowired
  AppUserRepository userRepository;

  public ChatMessageService(ChatMessageRepository messageRepository, JwtUtil jwtUtil,
      ChatRepository chatRepository,AppUserRepository userRepository) {
    this.messageRepository = messageRepository;
    this.jwtUtil = jwtUtil;
    this.chatRepository = chatRepository;
    this.userRepository = userRepository;
  }



  public CustomResponse getAllMessages(Long chatId){

    List<ChatMessage>  allMessages = messageRepository.findByChatId(chatId);
    return new CustomResponse(allMessages, CustomStatus.SUCCESS, null);
  }

  public MessageResponse proccessMessage(MessageRequest request){

    Long senderId = (userRepository.findByUsername(request.getSenderUsername())
        .getId());
    Long fParticipant = chatRepository.findById(request.getChatId()).get().getFParticipant();
    Long sParticipant = chatRepository.findById(request.getChatId()).get().getSParticipant();
    Long receiverId;
    if(senderId == fParticipant){
      receiverId = sParticipant;
    }
    else{
      receiverId = fParticipant;
    }
    ChatMessage message = new ChatMessage(request.getContent(), senderId, receiverId,
        request.getChatId());
    messageRepository.save(message);
    return new MessageResponse(request.getChatId(),request.getSenderUsername(),
        request.getContent());
  }

  public void save(Map<String, String> message){
    Long chatId = Long.parseLong(message.get("chatId"));
    Long senderId = userRepository.findByUsername(message.get("sender")).getId();
    Chat chat = chatRepository.findById(Long.parseLong(message.get("chatId"))).get();
    Long receiverId;
    if (chat.getSParticipant() == senderId){
       receiverId = chat.getFParticipant();
    }
    else{
       receiverId = chat.getFParticipant();
    }

    ChatMessage newMessage = new ChatMessage(message.get("content"),senderId,receiverId,chatId);
    messageRepository.save(newMessage);

  }
}
