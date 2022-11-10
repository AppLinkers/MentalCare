package com.example.mentalCare.diagnose.service;


import com.example.mentalCare.diagnose.domain.*;
import com.example.mentalCare.diagnose.dto.*;
import com.example.mentalCare.diagnose.repository.*;
import com.example.mentalCare.user.domain.Player;
import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.repository.PlayerRepository;
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
    private final AnswerDetailRepository answerDetailRepository;
    private final AnswerDiagnoseRepository answerDiagnoseRepository;
    private final PlayerRepository playerRepository;
    private final DiagnoseRepository diagnoseRepository;
    private final QuestionRepository questionRepository;

    /**
     * 통합검사 질문 리스트 추출
     */
    @Transactional(readOnly = true)
    public List<GetDiagnoseRes> getAllQuestionOfIntegrationTest() {
        List<Diagnose> diagnoseList = diagnoseRepository.findAllByDeletedFalse();

        List<GetDiagnoseRes> result = new ArrayList<>();
        for (Diagnose diagnose : diagnoseList) {
            List<Question> questionList = questionRepository.findAllByDiagnoseIdAndDeletedFalse(diagnose.getId());

            List<GetQuestionRes> questionResList = new ArrayList<>();
            for (Question question : questionList) {
                GetQuestionRes getQuestionRes = GetQuestionRes.builder()
                        .questionId(question.getId())
                        .questionContext(question.getContext())
                        .build();

                questionResList.add(getQuestionRes);
            }

            GetDiagnoseRes getDiagnoseRes = GetDiagnoseRes.builder()
                    .diagnoseId(diagnose.getId())
                    .questionResList(questionResList)
                    .build();
            result.add(getDiagnoseRes);
        }

        return result;
    }

    /**
     * type 종류 리스트 추출
     */
    public List<GetDiagnoseInfoRes> getAllDiagnose() {
        List<Diagnose> diagnoseList = diagnoseRepository.findAllByDeletedFalse();

        List<GetDiagnoseInfoRes> result = new ArrayList<>();

        for (Diagnose diagnose : diagnoseList) {
            GetDiagnoseInfoRes getDiagnoseInfoRes = GetDiagnoseInfoRes.builder()
                    .diagnoseId(diagnose.getId())
                    .diagnoseTitle(diagnose.getTitle())
                    .build();
            result.add(getDiagnoseInfoRes);
        }

        return result;
    }

    /**
     * 특정 type 정보 추출
     */
    public GetDiagnoseInfoRes getDiagnoseInfoByDiagnoseId(Long diagnoseId) {
        Diagnose diagnose = diagnoseRepository.findById(diagnoseId).get();

        return GetDiagnoseInfoRes.builder()
                .diagnoseId(diagnose.getId())
                .diagnoseTitle(diagnose.getTitle())
                .build();
    }

    /**
     * type 검사 질문 리스트 추출
     */
    public GetDiagnoseRes getAllQuestionOfTypeTestByDiagnoseId(Long diagnoseId) {
        Diagnose diagnose = diagnoseRepository.findByIdAndAndDeletedFalse(diagnoseId);
        List<Question> questionList = questionRepository.findAllByDiagnoseIdAndDeletedFalse(diagnoseId);

        List<GetQuestionRes> questionResList = new ArrayList<>();
        for (Question question : questionList) {
            GetQuestionRes getQuestionRes = GetQuestionRes.builder()
                    .questionId(question.getId())
                    .questionContext(question.getContext())
                    .build();
            questionResList.add(getQuestionRes);
        }
        return GetDiagnoseRes.builder()
                .diagnoseId(diagnose.getId())
                .questionResList(questionResList)
                .build();
    }


    /**
     * 통합 진단 작성
     */
    @Transactional
    public void submitIntegrationAnswer(WriteAnswerReq req) {
        Player player = playerRepository.findPlayerByUserLoginId(req.getUserLoginId());
        Answer answer = Answer.builder()
                .player(player)
                .build();

        Answer savedAnswer = answerRepository.save(answer);

        player.addAnswer(savedAnswer);

        for (WriteAnswerDiagnoseReq writeAnswerDiagnoseReq : req.getWriteAnswerDiagnoseReqList()) {
            AnswerDiagnose answerDiagnose = AnswerDiagnose.builder()
                    .answer(savedAnswer)
                    .diagnose(diagnoseRepository.findById(writeAnswerDiagnoseReq.getDiagnoseId()).get()).build();

            AnswerDiagnose savedAnswerDiagnose = answerDiagnoseRepository.save(answerDiagnose);
            savedAnswer.addAnswerDiagnose(savedAnswerDiagnose);


            List<AnswerDetail> answerDetailList = new ArrayList<>();

            for (WriteAnswerDetailReq writeAnswerDetailReq : writeAnswerDiagnoseReq.getWriteAnswerDetailReqList()) {
                AnswerDetail answerDetail = AnswerDetail.builder()
                        .question(questionRepository.findById(writeAnswerDetailReq.getQuestionId()).get())
                        .answerDiagnose(savedAnswerDiagnose)
                        .answer(writeAnswerDetailReq.getAnswer()).build();

                answerDetailList.add(answerDetail);
            }

            List<AnswerDetail> savedAnswerDetailList = answerDetailRepository.saveAll(answerDetailList);
            savedAnswerDetailList.forEach(
                    savedAnswerDetail -> {
                        savedAnswerDiagnose.addAnswerDetail(savedAnswerDetail);
                    }
            );
        }
    }

    /**
     * 유형 진단 작성
     */
    @Transactional
    public void submitTypeAnswer(WriteAnswerReq req) {
        List<Answer> answerList = answerRepository.findAnswersByPlayerUserLoginIdOrderByUpdatedAt(req.getUserLoginId());

        if (answerList.isEmpty()) {
            submitIntegrationAnswer(req);
        } else {
            Answer answer = answerList.get(0);
            answer.setUpdatedAt(LocalDateTime.now());
            WriteAnswerDiagnoseReq writeAnswerDiagnoseReq = req.getWriteAnswerDiagnoseReqList().get(0);

            AnswerDiagnose answerDiagnose = answer.findAnswerDiagnoseByDiagnoseId(writeAnswerDiagnoseReq.getDiagnoseId());
            answerDiagnose.setUpdatedAt(LocalDateTime.now());

            List<WriteAnswerDetailReq> writeAnswerDetailReqList = writeAnswerDiagnoseReq.getWriteAnswerDetailReqList();
            List<AnswerDetail> answerDetailList = answerDiagnose.getAnswerDetailList();

            for (AnswerDetail answerDetail : answerDetailList) {
                for (WriteAnswerDetailReq writeAnswerDetailReq : writeAnswerDetailReqList) {
                    if (answerDetail.getQuestion().getId().equals(writeAnswerDetailReq.getQuestionId())) {
                        answerDetail.setAnswer(writeAnswerDetailReq.getAnswer());
                    }
                }
            }


        }

    }

    /**
     * 해당 사용자의 모든 진단 결과 불러오기
     */
    @Transactional(readOnly = true)
    public List<GetAnswerInfoRes> getAllAnswerByUserLoginId(String userLoginId) {
        List<Answer> answerList = answerRepository.findAnswersByPlayerUserLoginIdOrderByUpdatedAt(userLoginId);

        List<GetAnswerInfoRes> answerResList = new ArrayList<>();
        answerList.forEach(
                answer -> {
                    answerResList.add(
                            GetAnswerInfoRes.builder()
                                    .answerId(answer.getId())
                                    .answerDate(answer.getUpdatedAt())
                                    .build()
                    );
                }
        );

        return answerResList;
    }

    /**
     * 답변 결과 반환
     *
     * @param answerId
     * @return answerId에 해당하는 답변 결과
     */
    @Transactional(readOnly = true)
    public GetAnswerRes getAnswerByAnswerId(Long answerId) {

        Answer answer = answerRepository.findById(answerId).get();

        Player player = answer.getPlayer();
        User user = player.getUser();

        String playerName = user.getName();
        Integer playerAge = user.getAge();
        String playerPosition = playerRepository.findPlayerPositionByUserId(user.getId()).toString();

        LocalDateTime answerDate = answer.getCreatedAt();

        Double totalAverage = 0.0;

        List<GetAnswerDiagnoseRes> getAnswerDiagnoseResList = new ArrayList<>();
        for (AnswerDiagnose answerDiagnose : answer.getAnswerDiagnoseList()) {
            Diagnose diagnose = answerDiagnose.getDiagnose();

            String diagnoseTitle = diagnose.getTitle();

            LocalDateTime diagnoseAnswerDate = answerDiagnose.getUpdatedAt();

            Double diagnoseAverage = 0.0;

            List<GetAnswerDetailRes> getAnswerDetailResList = new ArrayList<>();
            for (AnswerDetail answerDetail : answerDiagnose.getAnswerDetailList()) {
                GetAnswerDetailRes getAnswerDetailRes = GetAnswerDetailRes.builder()
                        .questionKeyword(answerDetail.getQuestion().getKeyword())
                        .answer(answerDetail.getAnswer())
                        .build();

                getAnswerDetailResList.add(getAnswerDetailRes);
                diagnoseAverage += answerDetail.getAnswer();
            }
            diagnoseAverage /= getAnswerDetailResList.size();

            GetAnswerDiagnoseRes getAnswerDiagnoseRes = GetAnswerDiagnoseRes.builder()
                    .diagnoseTitle(diagnoseTitle)
                    .diagnoseAnswerDate(diagnoseAnswerDate)
                    .diagnoseAverage(diagnoseAverage)
                    .answerDetailResList(getAnswerDetailResList)
                    .build();

            getAnswerDiagnoseResList.add(getAnswerDiagnoseRes);

            totalAverage += diagnoseAverage;
        }
        totalAverage /= getAnswerDiagnoseResList.size();

        GetAnswerRes result = GetAnswerRes.builder()
                .playerName(playerName)
                .playerAge(playerAge)
                .playerPosition(playerPosition)
                .answerDate(answerDate)
                .totalAverage(totalAverage)
                .diagnoseResList(getAnswerDiagnoseResList)
                .build();

        return result;

    }

    /**
     * 사용자의 최근 결과 반환
     */
    @Transactional(readOnly = true)
    public Optional<GetAnswerRes> getAnswerResByUserLoginId(String userLoginId) {
        List<Long> answerIdList = answerRepository.findAnswersIdByPlayerUserLoginIdOrderByUpdatedAt(userLoginId);

        if (answerIdList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(getAnswerByAnswerId(answerIdList.get(0)));
        }
    }
}
