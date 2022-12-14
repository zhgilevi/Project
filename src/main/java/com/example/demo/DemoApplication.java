package com.example.demo;

import com.example.demo.controller.AppUserController;
import com.example.demo.entity.AppUser;
import com.example.demo.service.AppUserService;
import com.example.demo.util.SignUpRequest;
import java.lang.reflect.Type;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

  @Autowired
  AppUserService userService;

  @Bean
  public ModelMapper modelMapper(){
    return new ModelMapper();
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    SignUpRequest signUpRequest = new SignUpRequest("test","test_name","test_secondname","1234");
    AppUserController controller = new AppUserController(userService);
    //controller.registrUser(signUpRequest);


  }
}
