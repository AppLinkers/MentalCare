package com.example.mentalCare.player.profile.repository;

import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.test.dto.TestDiagnoseResultReadRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("select p.position from Player p where p.user.login_id = :userLoginId")
    String findPlayerPositionByUserLogin_id(String userLoginId);

    @Query("select p from Player p where p.user.login_id = :userLoginId")
    Player findPlayerByUserLoginId(String userLoginId);

    @Query("select p from Player p where p.user.team.id = :teamId")
    List<Player> findPlayerByUserTeamId(Long teamId);

    @Query("select new com.example.mentalCare.player.test.dto.TestDiagnoseResultReadRes(d.title, avg(adt.answer))"+
            "from Player p, Answer a, AnswerDiagnose  ad, AnswerDetail adt, Diagnose d " +
            "where p = a.player and a = ad.answer and ad = adt.answerDiagnose and ad.diagnose = d " +
            "and p.position = :position group by ad.diagnose"
    )
    List<TestDiagnoseResultReadRes> findTestDiagnoseResultByPosition(String position);

    @Query("select new com.example.mentalCare.player.test.dto.TestDiagnoseResultReadRes(d.title, avg(adt.answer))"+
            "from User u, Player p, Answer a, AnswerDiagnose  ad, AnswerDetail adt, Diagnose d " +
            "where p.user = u and p = a.player and a = ad.answer and ad = adt.answerDiagnose and ad.diagnose = d " +
            "and function('timestampdiff', YEAR, u.birthDate, current_date) = :age group by ad.diagnose"
    )
    List<TestDiagnoseResultReadRes> findTestDiagnoseResultByAge(Integer age);


    @Query("select new com.example.mentalCare.player.test.dto.TestDiagnoseResultReadRes(d.title, avg(adt.answer))" +
            "from User u, Player p, Answer a, AnswerDiagnose ad, AnswerDetail adt, Diagnose d " +
            "where p.user = u and p = a.player and a = ad.answer and ad = adt.answerDiagnose and ad.diagnose = d and p.user.login_id = :userLoginId " +
            "and substring(a.createdAt, 1, 7) = :ym group by ad.diagnose")
    List<TestDiagnoseResultReadRes> findTestDiagnoseResultByUserLoginIdAndYearMonth(String userLoginId, String ym);
}