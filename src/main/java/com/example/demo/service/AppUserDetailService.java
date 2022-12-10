package com.example.demo.service;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Date;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class AppUserDetailService implements UserDetails {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String username;


  private String fName;

  private String lName;

  private Date regDate;
  @JsonIgnore
  private String password;

  public AppUserDetailService(Long id, String username, String fName, String lName, Date regDate,
      String password) {
    this.id = id;
    this.username = username;
    this.fName = fName;
    this.lName = lName;
    this.regDate = regDate;
    this.password = password;
  }

  public static AppUserDetailService build(AppUser user){
    return new AppUserDetailService(
        user.getId(),
        user.getUsername(),
        user.getFName(),
        user.getLName(),
        user.getRegDate(),
        user.getPassword()
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
