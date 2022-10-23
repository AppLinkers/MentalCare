package com.example.mentalCare.diagnose.repository;

import com.example.mentalCare.diagnose.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("select a from Answer a where a.user.login_id = :userId")
    List<Answer> findAnswersByUserLoginId(String userId);

}