package com.example.mentalCare.player.test.service;

import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import com.example.mentalCare.player.test.domain.*;
import com.example.mentalCare.player.test.dto.*;
import com.example.mentalCare.player.test.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerTestService {

    private final AnswerRepository answerRepository;
    private final AnswerDiagnoseRepository answerDiagnoseRepository;
    private final AnswerDetailRepository answerDetailRepository;
    private final DiagnoseRepository diagnoseRepository;
    private final QuestionRepository questionRepository;
    private final PlayerRepository playerRepository;

    /**
     * 통합진단 질문 리스트 추출
     */
    @Transactional(readOnly = true)
    public List<DiagnoseReadRes> getTestAllDiagnoseRead() {
        List<Diagnose> diagnoseList = diagnoseRepository.findAllByDeletedFalse();

        List<DiagnoseReadRes> result = new ArrayList<>();
        for (Diagnose diagnose : diagnoseList) {
            List<Question> questionList = questionRepository.findAllByDiagnoseIdAndDeletedFalse(diagnose.getId());

            List<QuestionReadRes> questionReadResList = new ArrayList<>();
            for (Question question : questionList) {
                QuestionReadRes questionReadRes = QuestionReadRes.builder()
                        .id(question.getId())
                        .context(question.getContext())
                        .build();

                questionReadResList.add(questionReadRes);
            }

            DiagnoseReadRes diagnoseReadRes = DiagnoseReadRes.builder()
                    .id(diagnose.getId())
                    .questionReadResList(questionReadResList)
                    .build();

            result.add(diagnoseReadRes);
        }

        return result;
    }

    /**
     * 통합진단 답변 제출
     */
    @Transactional
    public void submitTestAll(String userLoginId, List<DiagnoseWriteReq> answerWriteReq) {
        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        Answer answer = Answer.builder()
                .player(player)
                .build();

        Answer savedAnswer = answerRepository.save(answer);

        player.addAnswer(savedAnswer);

        for (DiagnoseWriteReq diagnoseWriteReq : answerWriteReq) {
            AnswerDiagnose answerDiagnose = AnswerDiagnose.builder()
                    .answer(savedAnswer)
                    .diagnose(diagnoseRepository.findById(diagnoseWriteReq.getId()).get()).build();

            AnswerDiagnose savedAnswerDiagnose = answerDiagnoseRepository.save(answerDiagnose);
            savedAnswer.addAnswerDiagnose(savedAnswerDiagnose);


            List<AnswerDetail> answerDetailList = new ArrayList<>();

            for (QuestionWriteReq questionWriteReq : diagnoseWriteReq.getQuestionWriteReqList()) {
                AnswerDetail answerDetail = AnswerDetail.builder()
                        .question(questionRepository.findById(questionWriteReq.getId()).get())
                        .answerDiagnose(savedAnswerDiagnose)
                        .answer(questionWriteReq.getAnswer()).build();

                answerDetailList.add(answerDetail);
            }

            List<AnswerDetail> savedAnswerDetailList = answerDetailRepository.saveAll(answerDetailList);
            savedAnswerDiagnose.addAnswerDetailList(savedAnswerDetailList);
        }

    }

    /**
     * 유형 진단 선택 정보 조회
     */
    @Transactional(readOnly = true)
    public List<DiagnoseInfoReadRes> getAllDiagnoseInfoRead() {
        List<Diagnose> diagnoseList = diagnoseRepository.findAllByDeletedFalse();

        List<DiagnoseInfoReadRes> result = new ArrayList<>();


        for (Diagnose diagnose : diagnoseList) {
            DiagnoseInfoReadRes diagnoseInfoReadRes = DiagnoseInfoReadRes.builder()
                    .id(diagnose.getId())
                    .title(diagnose.getTitle())
                    .iconUrl(diagnose.getIconUrl())
                    .build();
            result.add(diagnoseInfoReadRes);
        }

        return result;
    }

    /**
     * 유횽 진단 질문 리스트 추출
     */
    @Transactional(readOnly = true)
    public TypeDiagnoseReadRes getTestTypeDiagnoseRead(Long diagnoseId) {

        Diagnose diagnose = diagnoseRepository.findByIdAndAndDeletedFalse(diagnoseId);
        List<Question> questionList = questionRepository.findAllByDiagnoseIdAndDeletedFalse(diagnoseId);

        List<QuestionReadRes> questionReadResList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionReadRes questionReadRes = QuestionReadRes.builder()
                    .id(question.getId())
                    .context(question.getContext())
                    .build();
            questionReadResList.add(questionReadRes);
        }
        return TypeDiagnoseReadRes.builder()
                .id(diagnose.getId())
                .title(diagnose.getTitle())
                .iconUrl(diagnose.getIconUrl())
                .questionReadResList(questionReadResList)
                .build();

    }

    public Boolean isLastAnswerExist(String userLoginId){
        List<Answer> answerList = answerRepository.findAnswersByPlayerUserLoginIdOrderByUpdatedAt(userLoginId);
        if(answerList.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 유형진단 답변 제출
     * 최근 Answer 에 덮어 씌우기
     */
    @Transactional
    public void submitTestType(String userLoginId, DiagnoseWriteReq diagnoseWriteReq) {
        List<Answer> answerList = answerRepository.findAnswersByPlayerUserLoginIdOrderByUpdatedAt(userLoginId);

        if (answerList.isEmpty()) {
            // 이전 답변이 없다면,
            List<DiagnoseWriteReq> diagnoseWriteReqList = List.of(diagnoseWriteReq);
            submitTestAll(userLoginId, diagnoseWriteReqList);
        } else {
            // 최근 답변에 덮어씌우기
            Answer answer = answerList.get(0);
            answer.setUpdatedAt(LocalDateTime.now());

            AnswerDiagnose answerDiagnose = answer.findAnswerDiagnoseByDiagnoseId(diagnoseWriteReq.getId());
            answerDiagnose.setUpdatedAt(LocalDateTime.now());

            List<QuestionWriteReq> questionWriteReqList = diagnoseWriteReq.getQuestionWriteReqList();
            List<AnswerDetail> answerDetailList = answerDiagnose.getAnswerDetailList();

            for (AnswerDetail answerDetail : answerDetailList) {
                for (QuestionWriteReq questionWriteReq : questionWriteReqList) {
                    if (answerDetail.getQuestion().getId().equals(questionWriteReq.getId())) {
                        answerDetail.setAnswer(questionWriteReq.getAnswer());
                    }
                }
            }
        }

    }

    /**
     * 진단 결과 (목록)
     */
    @Transactional(readOnly = true)
    public List<TestResultInfoReadRes> getAllTestResultByUserLoginId(String userLoginId) {
        List<Answer> answerList = answerRepository.findAnswersByPlayerUserLoginIdOrderByUpdatedAt(userLoginId);

        List<TestResultInfoReadRes> result = new ArrayList<>();
        answerList.forEach(
                answer -> {
                    result.add(
                            TestResultInfoReadRes.builder()
                                    .id(answer.getId())
                                    .date(answer.getUpdatedAt().toLocalDate())
                                    .diagnoseTypeList(answer.findDiagnoseTypeOfAnswer())
                                    .build()
                    );

                }
        );

        return result;
    }

    /**
     * 진단 결과 (조회) - Player Info
     */
    @Transactional(readOnly = true)
    public TestResultPlayerInfoReadRes getPlayerInfoOfTestResult(Long answerId) {
        Answer answer = answerRepository.findById(answerId).get();

        Player player = answer.getPlayer();

        return TestResultPlayerInfoReadRes.builder()
                .name(player.getUser().getName())
                .age(player.getUser().getAge())
                .position(player.getPosition())
                .imgUrl(player.getUser().getImgUrl())
                .build();
    }

    /**
     * 진단 결과 (조회) - Test Result Info
     */
    @Transactional(readOnly = true)
    public TestResultReadRes getTestResult(Long answerId) {
        Answer answer = answerRepository.findById(answerId).get();
        Double answerAvg = 0.0;

        List<DiagnoseResultReadOfTestResultRes> diagnoseResultList = new ArrayList<>();

        for (AnswerDiagnose answerDiagnose : answer.getAnswerDiagnoseList()) {
            Double answerDiagnoseAvg = answerDiagnose.getAvg();
            answerAvg += answerDiagnoseAvg;

            List<QuestionResultReadOfDiagnoseResult> questionResultList = new ArrayList<>();

            for (AnswerDetail answerDetail : answerDiagnose.getAnswerDetailList()) {

                questionResultList.add(
                        QuestionResultReadOfDiagnoseResult.builder()
                                .keyword(answerDetail.getQuestion().getKeyword())
                                .answer(answerDetail.getAnswer())
                                .build()
                );
            }

            diagnoseResultList.add(
                    DiagnoseResultReadOfTestResultRes.builder()
                            .title(answerDiagnose.getDiagnose().getTitle())
                            .avg(answerDiagnoseAvg)
                            .questionResultList(questionResultList)
                            .build()
            );
        }

        answerAvg /= answer.getAnswerDiagnoseList().size();

        return TestResultReadRes.builder()
                .date(answer.getUpdatedAt().toLocalDate())
                .avg(answerAvg)
                .diagnoseResultList(diagnoseResultList)
                .build();

    }


}
