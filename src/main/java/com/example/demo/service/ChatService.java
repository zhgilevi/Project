package com.example.demo.service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Chat;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.ChatRepository;
import com.example.demo.util.CustomResponse;
import com.example.demo.util.CustomStatus;
import com.example.demo.util.JwtUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


  public Map<String, Object> addChat(ArrayList<String> users){
    Long fPart = Long.parseLong(users.get(0));
    Long sPart = Long.parseLong(users.get(1));
    Chat chat = chatRepository.findByFParticipantOrSParticipant(fPart, sPart);
    Map<String, Object> response= new HashMap<>();
    response.put("code", 0);
    if (chat == null){
      Chat newChat = new Chat(fPart, sPart);
      chatRepository.save(newChat);
      response.put("chatId", newChat.getId());
    }
    else{
      response.put("chatId", chat.getId());
    }
    return response;

  }


  public Map<String, Object> getChats(Long id){
    List<Chat> chats = chatRepository.getChats(id);
    Map<String, Object> response = new HashMap<>();
    response.put("data",new ArrayList<Object>());
    List<Object> tmp2 = new ArrayList<>();
    chats.forEach((chat)->{
      Map<String, Object> tmp = new HashMap<>();
      String fUsername = userRepository.findById(chat.getFParticipant()).get().getUsername();
      String sUsername = userRepository.findById(chat.getSParticipant()).get().getUsername();
      tmp.put("participants", new String[]{fUsername, sUsername});
      tmp.put("chatId", chat.getId());
      tmp2.add(tmp);
    });
    if (tmp2.isEmpty()){
      response.put("data", null);
      response.put("code",1);
    }
    else {
      response.put("data", tmp2);
      response.put("code",0);
    }

    return response;
  }



}
