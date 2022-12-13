package com.example.demo.service;

import com.example.demo.entity.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class AppUserDetailService implements UserDetails {

  private static final long serialVersionUID = 1L;

  private String username;


  @JsonIgnore
  private String password;

  public AppUserDetailService( String username,
      String password) {
    this.username = username;
    this.password = password;
  }

  public static AppUserDetailService build(AppUser user){
    return new AppUserDetailService(
        user.getUsername(),
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
