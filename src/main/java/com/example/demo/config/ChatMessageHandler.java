package com.example.demo.config;

import com.fasterxml.jackson.databind.util.JSONPObject;
import java.io.IOException;
import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatMessageHandler extends TextWebSocketHandler {
  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
    throws InterruptedException, IOException{
    String payload =  message.getPayload();
    JSONObject jsonObject = new JSONObject(payload);


  }


}
