package com.example.demo.config;

import com.example.demo.util.MyHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {




  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(new MyHandler(), "/ws")
        .setAllowedOrigins("*");
        // initial Request/Handshake interceptor



  }
}