package com.example.mentalCare.player.test.repository;

import com.example.mentalCare.consultant.consulting.dto.DiagnoseResultWithAnswerIdReadRes;
import com.example.mentalCare.player.test.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("select a from Answer a where a.player.user.id = :userId order by a.createdAt desc")
    List<Answer> findAnswersByPlayerUserIdOrderByCreatedAt(Long userId);

    Answer getFirstByPlayerUserIdOrderByCreatedAtDesc(Long userId);

    Boolean existsByPlayer_UserId(Long userId);

    @Query("select distinct cast(a.createdAt as LocalDate) " +
            "from Answer a, Player p, User u " +
            "where a.player.id = p.id and p.user.id = u.id and u.team.id = :teamId")
    List<LocalDate> findDistinctDateByTeamId(Long teamId);

    @Query("select new com.example.mentalCare.consultant.consulting.dto.DiagnoseResultWithAnswerIdReadRes(a.id, u.name, u.imgUrl, round(avg(adt.answer),2))" +
            "from Answer a, AnswerDiagnose ad, AnswerDetail adt, Player p, User u " +
            "where ad.id = adt.answerDiagnose.id and a.id = ad.answer.id and a.player.id = p.id and p.user.id = u.id and u.team.id = :teamId and cast(a.createdAt as LocalDate) = :answerDate group by a.id")
    List<DiagnoseResultWithAnswerIdReadRes> findAnswerAvgByTeamIdAndDate(Long teamId, LocalDate answerDate);

    @Query("select distinct cast(a.createdAt as LocalDate) " +
            "from Answer a, Player p " +
            "where a.player.id = p.id and p.consultant.id = :consultantId")
    List<LocalDate> findDistinctDateByConsultantId(Long consultantId);

    @Query("select new com.example.mentalCare.consultant.consulting.dto.DiagnoseResultWithAnswerIdReadRes(a.id, u.name, u.imgUrl, round(avg(adt.answer),2))" +
            "from Answer a, AnswerDiagnose ad, AnswerDetail adt, Player p, User u " +
            "where ad.id = adt.answerDiagnose.id and a.id = ad.answer.id and a.player.id = p.id and p.consultant.id = :consultantId and cast(a.createdAt as LocalDate) = :answerDate group by a.id")
    List<DiagnoseResultWithAnswerIdReadRes> findAnswerAvgByConsultantIdAndDate(Long consultantId, LocalDate answerDate);
}