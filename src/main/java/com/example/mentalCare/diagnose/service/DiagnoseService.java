package com.example.mentalCare.diagnose.service;


import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.diagnose.dto.*;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import com.example.mentalCare.player.test.domain.*;
import com.example.mentalCare.player.test.repository.AnswerRepository;
import com.example.mentalCare.player.test.repository.DiagnoseRepository;
import com.example.mentalCare.player.test.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiagnoseService {

    private final AnswerRepository answerRepository;
    private final PlayerRepository playerRepository;





//
//    /**
//     * 사용자의 최근 결과 반환
//     */
//    @Transactional(readOnly = true)
//    public Optional<GetAnswerRes> getAnswerResByUserLoginId(String userLoginId) {
//        List<Long> answerIdList = answerRepository.findAnswersIdByPlayerUserLoginIdOrderByUpdatedAt(userLoginId);
//
//        if (answerIdList.isEmpty()) {
//            return Optional.empty();
//        } else {
//            return Optional.of(getAnswerByAnswerId(answerIdList.get(0)));
//        }
//    }
}
