package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Setter
@Getter
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  private String username;

  private String fName;

  private String lName;

  private Date regDate;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  public AppUser(String username, String fName, String lName, String password) {
    this.username = username;
    this.fName = fName;
    this.lName = lName;
    this.password = password;
    this.regDate = new java.sql.Date(System.currentTimeMillis());
  }

  public AppUser() {
    this.regDate = new java.sql.Date(System.currentTimeMillis());
  }
}
