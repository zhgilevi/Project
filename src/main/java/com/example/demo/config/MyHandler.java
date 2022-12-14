package com.example.demo.config;

import com.example.demo.service.ChatMessageService;
import com.example.demo.service.ChatService;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@NoArgsConstructor
public class MyHandler extends TextWebSocketHandler {


  @Autowired
  private ChatMessageService messageService;
  private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();



  public MyHandler(ChatMessageService messageService){
    this.messageService = messageService;
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws InterruptedException, IOException, Exception {

    // parse message
    Map<String, String> value = new Gson().fromJson(message.getPayload(), Map.class);
    super.handleTextMessage(session,message);
    messageService.save(value);
    for (WebSocketSession webSocketSession: sessions){
      webSocketSession.sendMessage(message);//chatId content sender
    }

    }


  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    // the messages will be broadcasted to all users.
    super.afterConnectionEstablished(session);
    sessions.add(session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
  throws Exception{
    super.afterConnectionClosed(session, status);
    sessions.remove(session);
    // do something on connection closed
  }





}