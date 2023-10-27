package com.example.mentalCare.consultant.consulting.service;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.consultant.consulting.dto.*;
import com.example.mentalCare.consultant.profile.domain.Consultant;
import com.example.mentalCare.consultant.profile.repository.ConsultantRepository;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import com.example.mentalCare.player.test.domain.Answer;
import com.example.mentalCare.player.test.domain.Diagnose;
import com.example.mentalCare.player.test.repository.AnswerRepository;
import com.example.mentalCare.player.test.repository.DiagnoseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
}
