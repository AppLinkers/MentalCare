package com.example.mentalCare.consultant.consulting.service;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.consultant.consulting.dto.DiagnoseResultDateRes;
import com.example.mentalCare.consultant.consulting.dto.DiagnoseResultReadRes;
import com.example.mentalCare.consultant.profile.domain.Consultant;
import com.example.mentalCare.consultant.profile.repository.ConsultantRepository;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.test.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultingService {

    private final UserRepository userRepository;
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

                List<DiagnoseResultReadRes> diagnoseResultReadResList = answerRepository.findAnswerAvgByTeamIdAndDate(user.getTeam().getId(), answerDate);

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

            List<DiagnoseResultReadRes> diagnoseResultReadResList = answerRepository.findAnswerAvgByConsultantIdAndDate(consultant.getId(), answerDate);

            DiagnoseResultDateRes diagnoseResultDateRes = DiagnoseResultDateRes.builder()
                    .date(answerDate)
                    .count(diagnoseResultReadResList.size())
                    .diagnoseResultReadResList(diagnoseResultReadResList)
                    .build();

            response.add(diagnoseResultDateRes);
        }

        return response;
    }
}
