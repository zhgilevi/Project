package com.example.demo.repository;

import com.example.demo.entity.AppUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  public boolean existsByUsername(String username);

  public AppUser findByUsername(String username);

  @Query("SELECT e FROM AppUser e WHERE e.username like concat('%',:username,'%')"
      + "or e.fName like concat('%',:username,'%') or e.lName like concat('%',:username,'%') ")
  public List<AppUser> usernameContains(@Param("username") String username);


  @Query("update AppUser t set t.username= :username where t.id= :id")
  @Modifying
  public void updateUsername(@Param("username")String username, @Param("id") Long id);

  @Query("update AppUser t set t.fName= :fName where t.id= :id")
  @Modifying
  public void updateFName(@Param("fName")String fName, @Param("id") Long id);

  @Query("update AppUser t set t.lName= :lName where t.id= :id")
  @Modifying
  public void updateLName(@Param("lName")String lName, @Param("id") Long id);

  @Query("update AppUser t set t.password= :password where t.id= :id")
  @Modifying
  public void updatePassword(@Param("password")String password, @Param("id") Long id);




}
