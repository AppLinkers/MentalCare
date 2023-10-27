package com.example.mentalCare.player.test.repository;

import com.example.mentalCare.player.test.domain.AnswerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface AnswerDetailRepository extends JpaRepository<AnswerDetail, Long> {

    @Query("select adt " +
            "from AnswerDetail adt, AnswerDiagnose ad, Answer a " +
            "where adt.answerDiagnose.id = ad.id and ad.answer.id = a.id and a.player.user.id = :userId and a.createdAt = :answerDate and adt.question.id = :questionId")
    AnswerDetail findByUserIdAndAnswerDateAndQuestionId(Long userId, LocalDateTime answerDate, Long questionId);

}