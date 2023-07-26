package com.example.mentalCare.player.test.repository;

import com.example.mentalCare.player.test.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("select a from Answer a where a.player.user.id = :userId order by a.createdAt desc")
    List<Answer> findAnswersByPlayerUserIdOrderByCreatedAt(Long userId);

    Answer getFirstByPlayerUserIdOrderByCreatedAtDesc(Long userId);

    Boolean existsByPlayer_UserId(Long userId);

}