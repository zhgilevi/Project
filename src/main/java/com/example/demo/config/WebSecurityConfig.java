package com.example.demo.config;

import jakarta.servlet.http.HttpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
    return http.csrf().disable().authorizeRequests().anyRequest().permitAll()
        .and().build();
  }



}
