package com.example.mentalCare.consultant.consulting.service;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.consultant.consulting.dto.*;
import com.example.mentalCare.consultant.profile.domain.Consultant;
import com.example.mentalCare.consultant.profile.repository.ConsultantRepository;
import com.example.mentalCare.player.consulting.domain.RequestConsulting;
import com.example.mentalCare.player.consulting.repository.RequestConsultingRepository;
import com.example.mentalCare.player.feed.domain.Feed;
import com.example.mentalCare.player.feed.repository.FeedRepository;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import com.example.mentalCare.player.test.domain.Answer;
import com.example.mentalCare.player.test.domain.AnswerDetail;
import com.example.mentalCare.player.test.domain.Diagnose;
import com.example.mentalCare.player.test.domain.Question;
import com.example.mentalCare.player.test.dto.MonthlyResultReadRes;
import com.example.mentalCare.player.test.repository.AnswerDetailRepository;
import com.example.mentalCare.player.test.repository.AnswerRepository;
import com.example.mentalCare.player.test.repository.DiagnoseRepository;
import com.example.mentalCare.player.test.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class ConsultingService {

    private final DiagnoseRepository diagnoseRepository;
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final ConsultantRepository consultantRepository;
    private final AnswerRepository answerRepository;
    private final AnswerDetailRepository answerDetailRepository;
    private final QuestionRepository questionRepository;
    private final FeedRepository feedRepository;
    private final RequestConsultingRepository requestConsultingRepository;

    /**
     * 상담가가 속한 팀의 선수들의 날짜별 검사 결과 리스트
     */
    @Transactional(readOnly = true)
    public List<DiagnoseResultDateRes> getTeamPlayerDiagnoseResultByDate(String userLoginId) {
        List<DiagnoseResultDateRes> response = new ArrayList<>();

        User user = userRepository.findUserByLoginId(userLoginId).get();

        if (user.getTeam() != null) {
            List<LocalDate> answerDateList = answerRepository.findDistinctDateByTeamId(user.getTeam().getId());

            for (LocalDate answerDate : answerDateList) {

                List<DiagnoseResultWithAnswerIdReadRes> diagnoseResultReadResList = answerRepository.findAnswerAvgByTeamIdAndDate(user.getTeam().getId(), answerDate);

                DiagnoseResultDateRes diagnoseResultDateRes = DiagnoseResultDateRes.builder()
                        .date(answerDate)
                        .count(diagnoseResultReadResList.size())
                        .diagnoseResultReadResList(diagnoseResultReadResList)
                        .build();

                response.add(diagnoseResultDateRes);
            }
        }

        return response;
    }

    /**
     * 상담가가 담당하고 있는 개인 선수들의 날짜별 검사 결과 리스트
     */
    @Transactional(readOnly = true)
    public List<DiagnoseResultDateRes> getIndividualPlayerDiagnoseResultByDate(String userLoginId) {
        List<DiagnoseResultDateRes> response = new ArrayList<>();

        Consultant consultant = consultantRepository.findByUserLogin_id(userLoginId);

        List<LocalDate> answerDateList = answerRepository.findDistinctDateByConsultantId(consultant.getId());

        for (LocalDate answerDate : answerDateList) {

            List<DiagnoseResultWithAnswerIdReadRes> diagnoseResultReadResList = answerRepository.findAnswerAvgByConsultantIdAndDate(consultant.getId(), answerDate);

            DiagnoseResultDateRes diagnoseResultDateRes = DiagnoseResultDateRes.builder()
                    .date(answerDate)
                    .count(diagnoseResultReadResList.size())
                    .diagnoseResultReadResList(diagnoseResultReadResList)
                    .build();

            response.add(diagnoseResultDateRes);
        }

        return response;
    }

    /**
     * 상담가가 담당하고 있는 팀의 선수들의 최근 검사 결과 전체 평균 리스트
     */
    @Transactional(readOnly = true)
    public List<DiagnoseResultWithPlayerIdReadRes> getTotalDiagnoseResultByTeam(String userLoginId) {
        List<DiagnoseResultWithPlayerIdReadRes> response = new ArrayList<>();

        Consultant consultant = consultantRepository.findByUserLogin_id(userLoginId);

        if (consultant.getUser().getTeam() != null) {
            List<UserIdAndAnswerDate> userIdAndAnswerDateList = answerRepository.findUserIdAndLatestAnswerDateByTeamId(consultant.getUser().getTeam().getId());

            for (UserIdAndAnswerDate userIdAndAnswerDate : userIdAndAnswerDateList) {
                response.addAll(answerRepository.findAnswerAvgByUserIdAndAnswerDate(userIdAndAnswerDate.getUserId(), userIdAndAnswerDate.getAnswerDate()));
            }
        }

        Collections.sort(response);

        return response;
    }

    /**
     * 상담가가 담당하고 있는 팀의 선수들의 최근 검사 결과 유형별 평균 리스트
     */
    @Transactional(readOnly = true)
    public List<DiagnoseResultDiagnoseReadRes> getTypeDiagnoseResultByTeam(String userLoginId) {
        List<DiagnoseResultDiagnoseReadRes> response = new ArrayList<>();

        Consultant consultant = consultantRepository.findByUserLogin_id(userLoginId);

        List<Diagnose> diagnoseList = diagnoseRepository.findAllByDeletedFalse();

        if (consultant.getUser().getTeam() != null) {
            List<UserIdAndAnswerDate> userIdAndAnswerDateList = answerRepository.findUserIdAndLatestAnswerDateByTeamId(consultant.getUser().getTeam().getId());

            for (Diagnose diagnose : diagnoseList) {
                List<DiagnoseResultWithPlayerIdReadRes> diagnoseResultWithPlayerIdReadResList = new ArrayList<>();

                for (UserIdAndAnswerDate userIdAndAnswerDate : userIdAndAnswerDateList) {
                    diagnoseResultWithPlayerIdReadResList.addAll(answerRepository.findAnswerAvgByUserIdAndAnswerDateAndDiagnoseId(userIdAndAnswerDate.getUserId(), userIdAndAnswerDate.getAnswerDate(), diagnose.getId()));
                }

                Collections.sort(diagnoseResultWithPlayerIdReadResList);

                DiagnoseResultDiagnoseReadRes diagnoseResultDiagnoseReadRes = DiagnoseResultDiagnoseReadRes.builder()
                        .title(diagnose.getTitle())
                        .diagnoseResultReadResList(diagnoseResultWithPlayerIdReadResList)
                        .build();
                response.add(diagnoseResultDiagnoseReadRes);
            }
        }

        return response;
    }

    /**
     * 상담가가 담당하고 있는 팀의 선수들의 최근 검사 결과 전체 평균 리스트
     */
    @Transactional(readOnly = true)
    public List<DiagnoseResultWithPlayerIdReadRes> getTotalDiagnoseResultByIndividual(String userLoginId) {
        List<DiagnoseResultWithPlayerIdReadRes> response = new ArrayList<>();

        Consultant consultant = consultantRepository.findByUserLogin_id(userLoginId);
        List<UserIdAndAnswerDate> userIdAndAnswerDateList = answerRepository.findUserIdAndLatestAnswerDateByConsultantId(consultant.getId());

        for (UserIdAndAnswerDate userIdAndAnswerDate : userIdAndAnswerDateList) {
            response.addAll(answerRepository.findAnswerAvgByUserIdAndAnswerDate(userIdAndAnswerDate.getUserId(), userIdAndAnswerDate.getAnswerDate()));
        }

        Collections.sort(response);

        return response;
    }

    /**
     * 상담가가 담당하고 있는 선수들의 최근 검사 결과 유형별 평균 리스트
     */
    @Transactional(readOnly = true)
    public List<DiagnoseResultDiagnoseReadRes> getTypeDiagnoseResultByIndividual(String userLoginId) {
        List<DiagnoseResultDiagnoseReadRes> response = new ArrayList<>();

        Consultant consultant = consultantRepository.findByUserLogin_id(userLoginId);

        List<Diagnose> diagnoseList = diagnoseRepository.findAllByDeletedFalse();
        List<UserIdAndAnswerDate> userIdAndAnswerDateList = answerRepository.findUserIdAndLatestAnswerDateByConsultantId(consultant.getId());

        for (Diagnose diagnose : diagnoseList) {
            List<DiagnoseResultWithPlayerIdReadRes> diagnoseResultWithPlayerIdReadResList = new ArrayList<>();

            for (UserIdAndAnswerDate userIdAndAnswerDate : userIdAndAnswerDateList) {
                diagnoseResultWithPlayerIdReadResList.addAll(answerRepository.findAnswerAvgByUserIdAndAnswerDateAndDiagnoseId(userIdAndAnswerDate.getUserId(), userIdAndAnswerDate.getAnswerDate(), diagnose.getId()));
            }

            Collections.sort(diagnoseResultWithPlayerIdReadResList);

            DiagnoseResultDiagnoseReadRes diagnoseResultDiagnoseReadRes = DiagnoseResultDiagnoseReadRes.builder()
                    .title(diagnose.getTitle())
                    .diagnoseResultReadResList(diagnoseResultWithPlayerIdReadResList)
                    .build();
            response.add(diagnoseResultDiagnoseReadRes);
        }

        return response;
    }

    /**
     * 선수 정보 조회
     */
    @Transactional(readOnly = true)
    public DiagnoseResultPlayerInfoReadRes getPlayerInfoOfTestResult(Long playerId) {
        Player player = playerRepository.findById(playerId).get();

        StringBuilder nextMatchDDay = new StringBuilder();

        if (player.getNextMatch() != null) {
            Integer nextMatchDDayNum = (int) DAYS.between(player.getNextMatch(), LocalDate.now());

            if (nextMatchDDayNum >= 0) {
                nextMatchDDay.append("+ ");
            } else {
                nextMatchDDay.append("- ");
                nextMatchDDayNum *= -1;
            }
            nextMatchDDay.append(nextMatchDDayNum);
        }

        return DiagnoseResultPlayerInfoReadRes.builder()
                .name(player.getUser().getName())
                .age(player.getUser().getAge())
                .position(player.getPosition())
                .teamName(player.getUser().getTeam().getName())
                .nextMatchDDay(nextMatchDDay.toString())
                .imgUrl(player.getUser().getImgUrl())
                .build();
    }

    /**
     * 진단 결과 (목록)
     */
    @Transactional(readOnly = true)
    public List<DiagnoseResultInfoReadRes> getAllTestResultByPlayerId(Long playerId) {
        Long userId = playerRepository.findById(playerId).get().getUser().getId();
        List<Answer> answerList = answerRepository.findAnswersByPlayerUserIdOrderByCreatedAt(userId);

        List<DiagnoseResultInfoReadRes> result = new ArrayList<>();
        answerList.forEach(
                answer -> {
                    result.add(
                            DiagnoseResultInfoReadRes.builder()
                                    .id(answer.getId())
                                    .date(answer.getCreatedAt().toLocalDate())
                                    .build()
                    );

                }
        );

        return result;
    }

    /**
     * test Id 를 통해 선수 사용자 로그인 아이디 조회
     */
    @Transactional(readOnly = true)
    public String getPlayerUserLoginIdByTestId(Long id) {
        return answerRepository.findUserLoginIdByAnswerId(id);
    }

    public List<TestDiagnoseResultList> getTeamPlayerDiagnoseResult(String userLoginId) {
        List<TestDiagnoseResultList> response = new ArrayList<>();

        User user = userRepository.findUserByLoginId(userLoginId).get();

        if (user.getTeam() != null) {
            // 팀 소속 선수 별 가장 최근 검사 결과 조회
            List<UserIdAndAnswerDate> userIdAndAnswerDateList = answerRepository.findUserIdAndLatestAnswerDateByTeamId(user.getTeam().getId());
            Integer answerCnt = userIdAndAnswerDateList.size();

            List<Diagnose> diagnoseList = diagnoseRepository.findAllByDeletedFalse();

            for (Diagnose diagnose : diagnoseList) {
                String title = diagnose.getTitle();
                List<DiagnoseResultWithPlayerIdReadRes> diagnoseResultWithPlayerIdReadResList = new ArrayList<>();
                Double avg = 0.0;
                for (UserIdAndAnswerDate userIdAndAnswerDate : userIdAndAnswerDateList) {
                    for (DiagnoseResultWithPlayerIdReadRes diagnoseResultWithPlayerIdReadRes : answerRepository.findAnswerAvgByUserIdAndAnswerDateAndDiagnoseId(userIdAndAnswerDate.getUserId(), userIdAndAnswerDate.getAnswerDate(), diagnose.getId())) {
                        avg += diagnoseResultWithPlayerIdReadRes.getAvg();
                        diagnoseResultWithPlayerIdReadResList.add(diagnoseResultWithPlayerIdReadRes);
                    }
                }
                avg /= answerCnt;
                avg = Math.round(avg * 100.0) / 100.0;

                List<QuestionResultReadOfDiagnoseResult> questionResultList = new ArrayList<>();
                List<Question> questionList = questionRepository.findAllByDiagnoseIdAndDeletedFalse(diagnose.getId());
                for (Question question : questionList) {
                    Double questionAvg = 0.0;
                    for (UserIdAndAnswerDate userIdAndAnswerDate : userIdAndAnswerDateList) {
                        AnswerDetail answerDetail = answerDetailRepository.findByUserIdAndAnswerDateAndQuestionId(userIdAndAnswerDate.getUserId(), userIdAndAnswerDate.getAnswerDate(), question.getId());
                        questionAvg += answerDetail.getAnswer();
                    }
                    questionAvg /= answerCnt;
                    questionAvg = Math.round(questionAvg * 100.0) / 100.0;

                    questionResultList.add(
                            QuestionResultReadOfDiagnoseResult.builder()
                                    .keyword(question.getKeyword())
                                    .answer(questionAvg)
                                    .build()
                    );
                }

                Collections.sort(diagnoseResultWithPlayerIdReadResList);
                Collections.reverse(diagnoseResultWithPlayerIdReadResList);

                Integer lastIdx = 6;
                if (answerCnt < 6) {
                    lastIdx = answerCnt;
                }

                List<PlayerInfoAndAvgScore> worst6PlayerList = new ArrayList<>();
                for (int i = 0; i < lastIdx; i++) {
                    DiagnoseResultWithPlayerIdReadRes diagnoseResultWithPlayerIdReadRes = diagnoseResultWithPlayerIdReadResList.get(i);
                    worst6PlayerList.add(PlayerInfoAndAvgScore.builder()
                            .playerId(diagnoseResultWithPlayerIdReadRes.getPlayerId())
                            .name(diagnoseResultWithPlayerIdReadRes.getPlayerName())
                            .score(diagnoseResultWithPlayerIdReadRes.getAvg())
                            .build());
                }

                response.add(
                        TestDiagnoseResultList.builder()
                                .title(title)
                                .avg(avg)
                                .questionResultList(questionResultList)
                                .worst6PlayerList(worst6PlayerList)
                                .build()
                );
            }
        }

        return response;
    }

    @Transactional(readOnly = true)
    public List<DiagnoseMonthlyAvgReadRes> getTeamPlayerMonthlyTotalAvg(String userLoginId) {
        List<DiagnoseMonthlyAvgReadRes> response = new ArrayList<>();

        User user = userRepository.findUserByLoginId(userLoginId).get();

        if (user.getTeam() != null) {
            for (int i = 0; i < 6; i++) {
                YearMonth yearMonth = YearMonth.from(LocalDate.now().minusMonths(i)); // 현재 날짜부터 i달 전의 YearMonth 객체
                String formattedYearMonth = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")); // YearMonth를 원하는 형식으로 포맷

                DiagnoseMonthlyAvgReadRes monthlyTotalAvg = DiagnoseMonthlyAvgReadRes.builder()
                        .yearMonth(formattedYearMonth)
                        .avg(answerRepository.findMonthlyAvgByTeamIdAndYearMonth(user.getTeam().getId(), formattedYearMonth).orElse(0.0))
                        .build();

                response.add(monthlyTotalAvg);
            }
        }

        return response;
    }

    @Transactional(readOnly = true)
    public List<DiagnoseMonthlyTypeAvgReadRes> getTeamPlayerMonthlyTypeAvg(String userLoginId) {
        List<DiagnoseMonthlyTypeAvgReadRes> response = new ArrayList<>();

        User user = userRepository.findUserByLoginId(userLoginId).get();

        List<Diagnose> diagnoseList = diagnoseRepository.findAllByDeletedFalse();

        if (user.getTeam() != null) {
            for (Diagnose diagnose : diagnoseList) {
                String title = diagnose.getTitle();
                List<DiagnoseMonthlyAvgReadRes> diagnoseMonthlyAvgList = new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    YearMonth yearMonth = YearMonth.from(LocalDate.now().minusMonths(i)); // 현재 날짜부터 i달 전의 YearMonth 객체
                    String formattedYearMonth = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")); // YearMonth를 원하는 형식으로 포맷

                    DiagnoseMonthlyAvgReadRes monthlyTotalAvg = DiagnoseMonthlyAvgReadRes.builder()
                            .yearMonth(formattedYearMonth)
                            .avg(answerRepository.findMonthlyAvgByTeamIdAndYearMonthAndDiagnoseId(user.getTeam().getId(), formattedYearMonth, diagnose.getId()).orElse(0.0))
                            .build();

                    diagnoseMonthlyAvgList.add(monthlyTotalAvg);
                }

                response.add(
                        DiagnoseMonthlyTypeAvgReadRes.builder()
                                .title(title)
                                .monthlyResultList(diagnoseMonthlyAvgList)
                                .build()
                );
            }
        }

        return response;
    }

    @Transactional(readOnly = true)
    public List<TestDiagnoseResultList> getIndividualPlayerDiagnoseResult(String userLoginId) {
        List<TestDiagnoseResultList> response = new ArrayList<>();

        Consultant consultant = consultantRepository.findByUserLogin_id(userLoginId);

        List<UserIdAndAnswerDate> userIdAndAnswerDateList = answerRepository.findUserIdAndLatestAnswerDateByConsultantId(consultant.getId());
        Integer answerCnt = userIdAndAnswerDateList.size();

        List<Diagnose> diagnoseList = diagnoseRepository.findAllByDeletedFalse();

        for (Diagnose diagnose : diagnoseList) {
            String title = diagnose.getTitle();
            List<DiagnoseResultWithPlayerIdReadRes> diagnoseResultWithPlayerIdReadResList = new ArrayList<>();
            Double avg = 0.0;
            for (UserIdAndAnswerDate userIdAndAnswerDate : userIdAndAnswerDateList) {
                for (DiagnoseResultWithPlayerIdReadRes diagnoseResultWithPlayerIdReadRes : answerRepository.findAnswerAvgByUserIdAndAnswerDateAndDiagnoseId(userIdAndAnswerDate.getUserId(), userIdAndAnswerDate.getAnswerDate(), diagnose.getId())) {
                    avg += diagnoseResultWithPlayerIdReadRes.getAvg();
                    diagnoseResultWithPlayerIdReadResList.add(diagnoseResultWithPlayerIdReadRes);
                }
            }
            avg /= answerCnt;
            avg = Math.round(avg * 100.0) / 100.0;

            List<QuestionResultReadOfDiagnoseResult> questionResultList = new ArrayList<>();
            List<Question> questionList = questionRepository.findAllByDiagnoseIdAndDeletedFalse(diagnose.getId());
            for (Question question : questionList) {
                Double questionAvg = 0.0;
                for (UserIdAndAnswerDate userIdAndAnswerDate : userIdAndAnswerDateList) {
                    AnswerDetail answerDetail = answerDetailRepository.findByUserIdAndAnswerDateAndQuestionId(userIdAndAnswerDate.getUserId(), userIdAndAnswerDate.getAnswerDate(), question.getId());
                    questionAvg += answerDetail.getAnswer();
                }
                questionAvg /= answerCnt;
                questionAvg = Math.round(questionAvg * 100.0) / 100.0;

                questionResultList.add(
                        QuestionResultReadOfDiagnoseResult.builder()
                                .keyword(question.getKeyword())
                                .answer(questionAvg)
                                .build()
                );
            }

            Collections.sort(diagnoseResultWithPlayerIdReadResList);
            Collections.reverse(diagnoseResultWithPlayerIdReadResList);

            Integer lastIdx = 6;
            if (answerCnt < 6) {
                lastIdx = answerCnt;
            }

            List<PlayerInfoAndAvgScore> worst6PlayerList = new ArrayList<>();
            for (int i = 0; i < lastIdx; i++) {
                DiagnoseResultWithPlayerIdReadRes diagnoseResultWithPlayerIdReadRes = diagnoseResultWithPlayerIdReadResList.get(i);
                worst6PlayerList.add(PlayerInfoAndAvgScore.builder()
                        .playerId(diagnoseResultWithPlayerIdReadRes.getPlayerId())
                        .name(diagnoseResultWithPlayerIdReadRes.getPlayerName())
                        .score(diagnoseResultWithPlayerIdReadRes.getAvg())
                        .build());
            }

            response.add(
                    TestDiagnoseResultList.builder()
                            .title(title)
                            .avg(avg)
                            .questionResultList(questionResultList)
                            .worst6PlayerList(worst6PlayerList)
                            .build()
            );
        }


        return response;
    }

    @Transactional(readOnly = true)
    public List<DiagnoseMonthlyAvgReadRes> getIndividualPlayerMonthlyTotalAvg(String userLoginId) {
        List<DiagnoseMonthlyAvgReadRes> response = new ArrayList<>();

        Consultant consultant = consultantRepository.findByUserLogin_id(userLoginId);

        for (int i = 0; i < 6; i++) {
            YearMonth yearMonth = YearMonth.from(LocalDate.now().minusMonths(i)); // 현재 날짜부터 i달 전의 YearMonth 객체
            String formattedYearMonth = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")); // YearMonth를 원하는 형식으로 포맷

            DiagnoseMonthlyAvgReadRes monthlyTotalAvg = DiagnoseMonthlyAvgReadRes.builder()
                    .yearMonth(formattedYearMonth)
                    .avg(answerRepository.findMonthlyAvgByConsultantIdAndYearMonth(consultant.getId(), formattedYearMonth).orElse(0.0))
                    .build();

            response.add(monthlyTotalAvg);
        }

        return response;
    }

    @Transactional(readOnly = true)
    public List<DiagnoseMonthlyTypeAvgReadRes> getIndividualPlayerMonthlyTypeAvg(String userLoginId) {
        List<DiagnoseMonthlyTypeAvgReadRes> response = new ArrayList<>();

        Consultant consultant = consultantRepository.findByUserLogin_id(userLoginId);

        List<Diagnose> diagnoseList = diagnoseRepository.findAllByDeletedFalse();

        for (Diagnose diagnose : diagnoseList) {
            String title = diagnose.getTitle();
            List<DiagnoseMonthlyAvgReadRes> diagnoseMonthlyAvgList = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                YearMonth yearMonth = YearMonth.from(LocalDate.now().minusMonths(i)); // 현재 날짜부터 i달 전의 YearMonth 객체
                String formattedYearMonth = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")); // YearMonth를 원하는 형식으로 포맷

                DiagnoseMonthlyAvgReadRes monthlyTotalAvg = DiagnoseMonthlyAvgReadRes.builder()
                        .yearMonth(formattedYearMonth)
                        .avg(answerRepository.findMonthlyAvgByConsultantIdAndYearMonthAndDiagnoseId(consultant.getId(), formattedYearMonth, diagnose.getId()).orElse(0.0))
                        .build();

                diagnoseMonthlyAvgList.add(monthlyTotalAvg);
            }

            response.add(
                    DiagnoseMonthlyTypeAvgReadRes.builder()
                            .title(title)
                            .monthlyResultList(diagnoseMonthlyAvgList)
                            .build()
            );
        }

        return response;
    }

    @Transactional
    public void testRequest(String userLoginId) {
        Consultant consultant = consultantRepository.findByUserLogin_id(userLoginId);
        User user = consultant.getUser();

        List<Feed> feedList = new ArrayList<>();
        // 담당 Team 선수에게 Feed 작성
        if (user.getTeam() != null) {
            List<Player> teamPlayerList = playerRepository.findPlayerByUserTeamId(user.getTeam().getId());
            for (Player player : teamPlayerList) {
                Feed feed = Feed.builder()
                        .user(user)
                        .player(player)
                        .content(getTestRequestContent(player))
                        .build();
                feedList.add(feed);
            }
        }

        // 담당 개인 선수에게 Feed 작성
        List<Player> individualPlayerList = playerRepository.findPlayerByConsultantId(consultant.getId());
        for (Player player : individualPlayerList) {
            Feed feed = Feed.builder()
                    .user(user)
                    .player(player)
                    .content(getTestRequestContent(player))
                    .build();
            feedList.add(feed);
        }

        feedRepository.saveAll(feedList);

    }

    @Transactional
    public void testRequestByPlayerId(String userLoginId, Long playerId) {
        User user = userRepository.findUserByLoginId(userLoginId).get();
        Player player = playerRepository.findById(playerId).get();

        Feed feed = Feed.builder()
                .user(user)
                .player(player)
                .content(getTestRequestContent(player))
                .build();

        feedRepository.save(feed);
    }

    private String getTestRequestContent(Player player) {
        return "컨설턴트가 " + player.getUser().getName() + "님 에게 검사를 요청하였습니다.";
    }

    @Transactional(readOnly = true)
    public List<PlayerInfoReadRes> getIndividualPlayerConsultingRequest(String userLoginId) {

        List<PlayerInfoReadRes> response = new ArrayList<>();

        requestConsultingRepository.findByConsultantUserLogin_id(userLoginId).forEach(
                requestConsulting -> {
                    response.add(
                            PlayerInfoReadRes.builder()
                                    .id(requestConsulting.getPlayer().getId())
                                    .name(requestConsulting.getPlayer().getUser().getName())
                                    .imgUrl(requestConsulting.getPlayer().getUser().getImgUrl())
                                    .build()
                    );
                }
        );


        return response;
    }

    @Transactional
    public void individualPlayerConsultingRequest(String userLoginId, Long playerId, Boolean accept) {
        Consultant consultant = consultantRepository.findByUserLogin_id(userLoginId);
        Player player = playerRepository.findById(playerId).get();
        if (accept) {
            player.setConsultant(consultant);
            requestConsultingRepository.deleteAllByPlayer(player);
        } else {
            requestConsultingRepository.deleteByConsultantAndPlayer(consultant, player);
        }

        playerRepository.save(player);
    }
}
