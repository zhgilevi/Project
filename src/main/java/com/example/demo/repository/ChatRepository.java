package com.example.demo.repository;

import com.example.demo.entity.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {


  @Query("select t from Chat t  where t.fParticipant=  :participant or t.sParticipant= :participant")
  public List<Chat> findByFParticipantOrSParticipant(@Param("participant") Long participant);
}
