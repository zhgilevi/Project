package com.example.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.entity.AppUser;
import com.example.demo.payload.AppUserDto;
import com.example.demo.service.AppUserService;
import com.example.demo.service.ChatService;
import com.example.demo.util.ContainerResponse;
import com.example.demo.util.CustomResponse;
import com.example.demo.util.CustomStatus;
import com.example.demo.util.EntityResponse;
import com.example.demo.util.IdList;
import com.example.demo.util.IdRequest;
import com.example.demo.util.LoginRequest;
import com.example.demo.util.LoginResponse;
import com.example.demo.util.MessageResponse;
import com.example.demo.util.SearchRequest;
import com.example.demo.util.SignUpRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.awt.image.AreaAveragingScaleFilter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class AppUserController {


  private final AppUserService userService;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  ChatService chatService;



  public AppUserController(AppUserService userService) {
    super();
    this.userService = userService;
  }

  @PostMapping("/signup")
  public LoginResponse registrUser(@RequestBody SignUpRequest userInfo) {
    AppUser user = new AppUser(userInfo.getUsername(), userInfo.getFName(),
        userInfo.getLName(), userInfo.getPassword());



    AppUser userSaved = userService.createUser(user);
    if (userSaved == null){
      return new LoginResponse(2);
    }
    return new LoginResponse(0);

  }

  @PostMapping("/signin")
  public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {



    Map<String, String> data = userService.loginUser(loginRequest);
    if (data == null){
      return new LoginResponse(2);
    }



    return new LoginResponse(0, data);
  }

  @PostMapping("/all")
  public Map<String, Object> getAll(@RequestBody String token){


    return userService.getAll(token);
  }

  @GetMapping("/user/{id}")
  public CustomResponse getUser(@PathVariable String id){
    Long userId = Long.parseLong(id);
    return userService.getUser(userId);
  }

  @PostMapping("/search")
  public Map<String, Object> findUser(
      @RequestBody SearchRequest request){
    //token validation

    return userService.searchUser(request.getUsername(), request.getToken());

  }

  //participants
  @PostMapping("/addchat")
  public Map<String, Object> createChat(@RequestBody IdList idList ){
    return chatService.addChat(idList.getParticipants());

  }

  @PostMapping("/chats")
  public Map<String, Object> getChats(@RequestBody IdRequest id){
    //Long longId = Long.parseLong(id);
    return chatService.getChats(id.getId());
  }

  @PostMapping("/messages")
  public List<MessageResponse> allMesages(@RequestBody IdRequest id){

    return chatService.allMessages(id.getId());
  }





}
