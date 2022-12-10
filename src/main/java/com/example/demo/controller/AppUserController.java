package com.example.demo.controller;

import com.example.demo.entity.AppUser;
import com.example.demo.service.AppUserService;
import com.example.demo.util.CustomResponse;
import com.example.demo.util.CustomStatus;
import com.example.demo.util.LoginRequest;
import com.example.demo.util.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class AppUserController {


  private final AppUserService userService;


  public AppUserController(AppUserService userService) {
    this.userService = userService;
  }

  @PostMapping("/registr")
  public CustomResponse registrUser(@RequestBody SignUpRequest userInfo) {
    AppUser user = new AppUser(userInfo.getUsername(), userInfo.getFName(),
        userInfo.getLName(), userInfo.getPassword());
    System.out.println(userInfo.getFName());
    return userService.createUser(user);
  }

  @PostMapping("/signup")
  public CustomResponse loginUser(@RequestBody LoginRequest loginRequest) {
    return userService.loginUser(loginRequest);
  }

  @GetMapping("/all")
  public CustomResponse getAll(){
    return userService.getAll();
  }


}
