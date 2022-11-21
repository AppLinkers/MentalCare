package com.example.mentalCare.player.test.repository;

import com.example.mentalCare.player.test.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("select a from Answer a where a.player.user.login_id = :userLoginId order by a.updatedAt desc")
    List<Answer> findAnswersByPlayerUserLoginIdOrderByUpdatedAt(String userLoginId);

    @Query("select a.id from Answer a where a.player.user.login_id = :userLoginId order by a.updatedAt desc")
    List<Long> findAnswersIdByPlayerUserLoginIdOrderByUpdatedAt(String userLoginId);


}