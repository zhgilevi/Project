package com.example.demo.service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Chat;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.ChatRepository;
import com.example.demo.util.CustomResponse;
import com.example.demo.util.CustomStatus;
import com.example.demo.util.JwtUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatService {

  @Autowired
  private ChatRepository chatRepository;

  @Autowired
  private AppUserRepository userRepository;

  @Autowired
  JwtUtil jwtUtil;

  public ChatService(ChatRepository chatRepository) {
    this.chatRepository = chatRepository;
  }


  public CustomResponse getAllChats(String username){
    jwtUtil.getUserNameFromJwtToken(username);
    AppUser user = userRepository.findByUsername(username);
    List<Chat> chats = chatRepository.findByFParticipantOrSParticipant(user.getId());
    return new CustomResponse(chats, CustomStatus.SUCCESS, null);


  }



}
