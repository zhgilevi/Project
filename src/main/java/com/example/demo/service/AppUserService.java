package com.example.demo.service;


import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.util.CustomResponse;
import com.example.demo.util.CustomStatus;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.LoginRequest;
import com.example.demo.util.LoginResponse;
import com.example.demo.util.UpdateRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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


  public AppUser createUser(AppUser user) {

    if (userRepository.existsByUsername(user.getUsername())) {
      return null;
    }

    userRepository.save(user);
    return user;
  }

  public Map<String, String> loginUser(LoginRequest loginRequest) {
    AppUser user = userRepository.findByUsername(loginRequest.getUsername());

    if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
      return null;
    }
    Map<String, String> responseMap = new HashMap<>();
    responseMap.put("id",Long.toString(user.getId()));
    responseMap.put("username", user.getUsername());
    responseMap.put("fName", user.getFName());
    responseMap.put("lName", user.getLName());
    responseMap.put("regDate", user.getRegDate().toString());
    String token = jwtUtil.generateJwtToken(loginRequest.getUsername());
    responseMap.put("token", token);

    return responseMap;
  }

  public Map<String, Object> getAll(String token){
    if (!jwtUtil.validateJwtToken(token)){
      Map<String, Object> response = new HashMap<>();
      response.put("code", 3);
      return response;
    }
    ArrayList<AppUser> users = new ArrayList<>();
    userRepository.findAll().forEach(user -> users.add(user) );
    Map<String, Object> response = new HashMap<>();
    response.put("code",0);
    response.put("data", users);
    return response;

  }

  public CustomResponse<List<AppUser>> getUser(Long id){
    AppUser user = userRepository.findById(id).get();
    List<AppUser> response = new ArrayList<>();
    response.add(user);
    if (user == null){
      return new CustomResponse(null,CustomStatus.NOT_FOUND,null);
    }
    return new CustomResponse(response,CustomStatus.SUCCESS,null);
  }

  public Map<String, Object> searchUser(String username, String token) {
    List<AppUser> users = userRepository.usernameContains(username);
    Map<String, Object> respones = new HashMap<>();
    if (users == null){
      respones.put("code",1);
      return respones;
    }
    respones.put("code",0);
    respones.put("data",users);
    return respones;
  }


  public LoginResponse updateUser(UpdateRequest request) {

    if (!request.getUsername().isBlank()){
      if (userRepository.existsByUsername(request.getUsername())){
        return new LoginResponse(2);
      }
      userRepository.updateUsername(request.getUsername(), request.getId());
    }
    if (!request.getFName().isBlank()){
      userRepository.updateFName(request.getFName(), request.getId());
    }
    if(!request.getLName().isBlank()){
      userRepository.updateLName(request.getLName(), request.getId());
    }
    if(!request.getPassword().isBlank()){
      userRepository.updatePassword(request.getFName(), request.getId());
    }

    AppUser user = userRepository.findById(request.getId()).get();
    Map<String, String> responseMap = new HashMap<>();
    responseMap.put("id",Long.toString(user.getId()));
    responseMap.put("username", user.getUsername());
    responseMap.put("fName", user.getFName());
    responseMap.put("lName", user.getLName());
    responseMap.put("regDate", user.getRegDate().toString());
    String token = jwtUtil.generateJwtToken(request.getUsername());
    responseMap.put("token", token);
    LoginResponse response=new LoginResponse(0, responseMap);

    return response;
  }


}
