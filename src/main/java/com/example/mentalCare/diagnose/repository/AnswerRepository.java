package com.example.mentalCare.diagnose.repository;

import com.example.mentalCare.diagnose.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {



    @Query("select a from Answer a where a.player.user.login_id = :userLoginId")
    List<Answer> findAnswersByPlayerUserLoginId(String userLoginId);

    @Query("select a from Answer a where a.player.user.login_id = :userLoginId order by a.updatedAt desc")
    List<Answer> findAnswersByPlayerUserLoginIdOOrderByUpdatedAt(String userLoginId);


}