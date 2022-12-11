package com.example.demo;

import com.example.demo.controller.AppUserController;
import com.example.demo.entity.AppUser;
import com.example.demo.service.AppUserService;
import com.example.demo.util.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

  @Autowired
  AppUserService userService;

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    SignUpRequest signUpRequest = new SignUpRequest("test","test_name","test_secondname","1234");
    AppUserController controller = new AppUserController(userService);
    controller.registrUser(signUpRequest);
    int code =controller.getAll().getCode();
    System.out.println(code);

  }
}
