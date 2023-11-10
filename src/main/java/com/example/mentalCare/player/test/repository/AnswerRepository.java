package com.example.mentalCare.player.test.repository;

import com.example.mentalCare.consultant.consulting.dto.DiagnoseResultWithAnswerIdReadRes;
import com.example.mentalCare.consultant.consulting.dto.DiagnoseResultWithPlayerIdReadRes;
import com.example.mentalCare.consultant.consulting.dto.UserIdAndAnswerDate;
import com.example.mentalCare.player.test.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("select a from Answer a where a.player.user.id = :userId order by a.createdAt desc")
    List<Answer> findAnswersByPlayerUserIdOrderByCreatedAt(Long userId);

    Answer getFirstByPlayerUserIdOrderByCreatedAtDesc(Long userId);

    Boolean existsByPlayer_UserId(Long userId);

    @Query("select distinct cast(a.createdAt as LocalDate) " +
            "from Answer a, Player p, User u " +
            "where a.player.id = p.id and p.user.id = u.id and u.team.id = :teamId")
    List<LocalDate> findDistinctDateByTeamId(Long teamId);

    @Query("select new com.example.mentalCare.consultant.consulting.dto.DiagnoseResultWithAnswerIdReadRes(a.id, p.id ,u.name, u.imgUrl, round(avg(adt.answer),2))" +
            "from Answer a, AnswerDiagnose ad, AnswerDetail adt, Player p, User u " +
            "where ad.id = adt.answerDiagnose.id and a.id = ad.answer.id and a.player.id = p.id and p.user.id = u.id and u.team.id = :teamId and cast(a.createdAt as LocalDate) = :answerDate group by a.id")
    List<DiagnoseResultWithAnswerIdReadRes> findAnswerAvgByTeamIdAndDate(Long teamId, LocalDate answerDate);

    @Query("select distinct cast(a.createdAt as LocalDate) " +
            "from Answer a, Player p " +
            "where a.player.id = p.id and p.consultant.id = :consultantId")
    List<LocalDate> findDistinctDateByConsultantId(Long consultantId);

    @Query("select new com.example.mentalCare.consultant.consulting.dto.DiagnoseResultWithAnswerIdReadRes(a.id, p.id, u.name, u.imgUrl, round(avg(adt.answer),2))" +
            "from Answer a, AnswerDiagnose ad, AnswerDetail adt, Player p, User u " +
            "where ad.id = adt.answerDiagnose.id and a.id = ad.answer.id and a.player.id = p.id and p.consultant.id = :consultantId and cast(a.createdAt as LocalDate) = :answerDate group by a.id")
    List<DiagnoseResultWithAnswerIdReadRes> findAnswerAvgByConsultantIdAndDate(Long consultantId, LocalDate answerDate);

    @Query("select  new com.example.mentalCare.consultant.consulting.dto.UserIdAndAnswerDate(u.id, max(a.createdAt))" +
            "from Answer a, Player p, User u " +
            "where a.player.id = p.id and p.user.id = u.id and u.team.id = :teamId group by u.id ")
    List<UserIdAndAnswerDate> findUserIdAndLatestAnswerDateByTeamId(Long teamId);

    @Query("select new com.example.mentalCare.consultant.consulting.dto.DiagnoseResultWithPlayerIdReadRes(round (avg(adt.answer), 2), p.id, u.name, u.imgUrl) " +
            "from Answer a, AnswerDiagnose ad, AnswerDetail adt, Player p, User u " +
            "where ad.id = adt.answerDiagnose.id and a.id = ad.answer.id and a.player.id = p.id and p.user.id = u.id and u.id = :userId and a.createdAt = :answerDate")
    List<DiagnoseResultWithPlayerIdReadRes> findAnswerAvgByUserIdAndAnswerDate(Long userId, LocalDateTime answerDate);

    @Query("select  new com.example.mentalCare.consultant.consulting.dto.UserIdAndAnswerDate(p.user.id, max(a.createdAt))" +
            "from Answer a, Player p " +
            "where a.player.id = p.id and p.consultant.id = :consultantId group by p.id ")
    List<UserIdAndAnswerDate> findUserIdAndLatestAnswerDateByConsultantId(Long consultantId);

    @Query("select new com.example.mentalCare.consultant.consulting.dto.DiagnoseResultWithPlayerIdReadRes(round (avg(adt.answer), 2), p.id, u.name, u.imgUrl) " +
            "from Answer a, AnswerDiagnose ad, AnswerDetail adt, Player p, User u " +
            "where ad.id = adt.answerDiagnose.id and a.id = ad.answer.id and a.player.id = p.id and p.user.id = u.id and u.id = :userId and a.createdAt = :answerDate and ad.diagnose.id = :diagnoseId")
    List<DiagnoseResultWithPlayerIdReadRes> findAnswerAvgByUserIdAndAnswerDateAndDiagnoseId(Long userId, LocalDateTime answerDate, Long diagnoseId);

    @Query("select u.login_id from User u, Player p, Answer a where p.user.id = u.id and a.player.id = p.id and a.id = :answerId")
    String findUserLoginIdByAnswerId(Long answerId);

    @Query("select round(avg(adt.answer), 2) " +
            "from Answer a, AnswerDiagnose ad, AnswerDetail adt, Player p, User u " +
            "where ad.id = adt.answerDiagnose.id and a.id = ad.answer.id and a.player.id = p.id and p.user.id = u.id and u.team.id = :teamId and substring(a.createdAt, 1, 7) = :formattedYearMonth")
    Optional<Double> findMonthlyAvgByTeamIdAndYearMonth(Long teamId, String formattedYearMonth);

    @Query("select round(avg(adt.answer), 2) " +
            "from Answer a, AnswerDiagnose ad, AnswerDetail adt, Player p, User u " +
            "where ad.id = adt.answerDiagnose.id and a.id = ad.answer.id and a.player.id = p.id and p.user.id = u.id and u.team.id = :teamId and substring(a.createdAt, 1, 7) = :formattedYearMonth and ad.diagnose.id = :diagnoseId")
    Optional<Double> findMonthlyAvgByTeamIdAndYearMonthAndDiagnoseId(Long teamId, String formattedYearMonth, Long diagnoseId);

    @Query("select round(avg(adt.answer), 2) " +
            "from Answer a, AnswerDiagnose ad, AnswerDetail adt, Player p " +
            "where ad.id = adt.answerDiagnose.id and a.id = ad.answer.id and a.player.id = p.id and p.consultant.id = :consultantId and substring(a.createdAt, 1, 7) = :formattedYearMonth")
    Optional<Double> findMonthlyAvgByConsultantIdAndYearMonth(Long consultantId, String formattedYearMonth);

    @Query("select round(avg(adt.answer), 2) " +
            "from Answer a, AnswerDiagnose ad, AnswerDetail adt, Player p " +
            "where ad.id = adt.answerDiagnose.id and a.id = ad.answer.id and a.player.id = p.id and p.consultant.id = :consultantId and substring(a.createdAt, 1, 7) = :formattedYearMonth and ad.diagnose.id = :diagnoseId")
    Optional<Double> findMonthlyAvgByConsultantIdAndYearMonthAndDiagnoseId(Long consultantId, String formattedYearMonth, Long diagnoseId);
}