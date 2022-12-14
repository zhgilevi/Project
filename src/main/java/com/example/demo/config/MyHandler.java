package com.example.demo.config;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyHandler extends TextWebSocketHandler {
  List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
  int messagecount = 0;

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws InterruptedException, IOException {

    // parse message
    Map<String, String> value = new Gson().fromJson(message.getPayload(), Map.class);

    // send message to all sessions
      // do something with the sent object

      messagecount++;
      // create object myMessageNumberObject = {type: 'messageNumber', messagecount: messagecount}
      // session.sendMessage(new TextMessage(myMessageNumberObject))

      // emit message with type='message'
      session.sendMessage(new TextMessage(message.getPayload()));
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

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
  throws IOException, Exception{
    super.handleMessage(session, message);
    for (WebSocketSession webSocketSession: sessions){
      webSocketSession.sendMessage(message);
    }
    // handle binary message
  }



}