package com.example.demo.repository;

import com.example.demo.entity.AppUser;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  public boolean existsByUsername(String username);

  public AppUser findAppUserByUsername(String username);


}
