//package com.example.demo.service;
//
//
//import com.example.demo.entity.Chat;
//import com.example.demo.entity.ChatMessage;
//import com.example.demo.repository.AppUserRepository;
//import com.example.demo.repository.ChatMessageRepository;
//import com.example.demo.repository.ChatRepository;
//import com.example.demo.util.CustomResponse;
//import com.example.demo.util.CustomStatus;
//import com.example.demo.util.JwtUtil;
//import com.example.demo.util.MessageRequest;
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@Transactional
//public class ChatMessageService {
//
//  @Autowired
//  ChatMessageRepository messageRepository;
//
//  @Autowired
//  JwtUtil jwtUtil;
//
//  @Autowired
//  ChatRepository chatRepository;
//
//  @Autowired
//  AppUserRepository userRepository;
//
//  public ChatMessageService(ChatMessageRepository messageRepository, JwtUtil jwtUtil,
//      ChatRepository chatRepository,AppUserRepository userRepository) {
//    this.messageRepository = messageRepository;
//    this.jwtUtil = jwtUtil;
//    this.chatRepository = chatRepository;
//    this.userRepository = userRepository;
//  }
//
//  public ChatMessage save(MessageRequest messageRequest){
//    String senderUsername = jwtUtil.getUserNameFromJwtToken(messageRequest.getToken());
//    Long senderId = userRepository.findByUsername(senderUsername).getId();
//    //Chat chat = chatRepository.findByFParticipantOrSParticipant(senderId);
//    Chat chat =chatRepository.findById(messageRequest.getChatId()).get();
//    Long receiverId;
//    if(senderId == chat.getParticipant()[0]){
//      receiverId = chat.getParticipant()[1];
//    }else{
//      receiverId = chat.getParticipant()[0];
//    }
//
//    ChatMessage message = new ChatMessage(messageRequest.getMessageText(),
//        senderId, receiverId, chat.getId());/*text sender receiver chatId*/
//    messageRepository.save(message);
//    return message;
//  }
//
//  public CustomResponse getAllMessages(Long chatId){
//
//    List<ChatMessage>  allMessages = messageRepository.findByChatId(chatId);
//    return new CustomResponse(allMessages, CustomStatus.SUCCESS, null);
//  }
//}
