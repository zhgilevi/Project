package com.example.demo.service;


import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.util.CustomResponse;
import com.example.demo.util.CustomStatus;
import com.example.demo.util.EntityResponse;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.LoginRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AppUserService {

  @Autowired
  private AppUserRepository userRepository;

  public AppUserService(AppUserRepository userRepository, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
  }

  @Autowired
  JwtUtil jwtUtil;


  public CustomResponse createUser(AppUser user) {

    if (userRepository.existsByUsername(user.getUsername())) {
      return new CustomResponse<>(null, CustomStatus.EXCEPTION, null);
    }

    userRepository.save(user);
    return new CustomResponse(null, CustomStatus.SUCCESS, null);
  }

  public CustomResponse loginUser(LoginRequest loginRequest) {
    System.out.println(loginRequest.getUsername());
    AppUser user = userRepository.findByUsername(loginRequest.getUsername());

    if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
      return new CustomResponse(null, CustomStatus.NOT_FOUND, null);
    }
    String token = jwtUtil.generateJwtToken(loginRequest.getUsername());
    return new CustomResponse(null, CustomStatus.SUCCESS, token);
  }

  public CustomResponse getAll(){
    ArrayList<AppUser> users = new ArrayList<>();
    userRepository.findAll().forEach(user -> users.add(user) );
    return new CustomResponse(users,CustomStatus.SUCCESS,null);

  }

  public CustomResponse getUser(Long id){
    AppUser user = userRepository.findById(id).get();
    List<AppUser> response = new ArrayList<>();
    response.add(user);
    if (user == null){
      return new CustomResponse(null,CustomStatus.NOT_FOUND,null);
    }
    return new CustomResponse(response,CustomStatus.SUCCESS,null);
  }

  public CustomResponse searchUser(String username) {
    AppUser user = userRepository.findByUsername(username);
    List<AppUser> response = new ArrayList<>();
    response.add(user);
    if (user == null){
      return new CustomResponse(null, CustomStatus.NOT_FOUND, null);
    }
    return new CustomResponse(response,CustomStatus.SUCCESS,null);
  }


}
