package com.example.mentalCare.player.test.service;

import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import com.example.mentalCare.player.test.domain.*;
import com.example.mentalCare.player.test.dto.*;
import com.example.mentalCare.player.test.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class PlayerTestService {

    private final AnswerRepository answerRepository;
    private final AnswerDiagnoseRepository answerDiagnoseRepository;
    private final AnswerDetailRepository answerDetailRepository;
    private final DiagnoseRepository diagnoseRepository;
    private final QuestionRepository questionRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

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

                List<QuestionDetailReadRes> questionDetailReadResList = new ArrayList<>();
                for (QuestionDetail questionDetail : question.getQuestionDetailList()){
                    questionDetailReadResList.add(questionDetail.toQuestionDetailReadRes());
                }
                QuestionReadRes questionReadRes = QuestionReadRes.builder()
                        .id(question.getId())
                        .context(question.getContext())
                        .questionDetailReadResList(questionDetailReadResList)
                        .build();

                questionReadResList.add(questionReadRes);
            }

            DiagnoseReadRes diagnoseReadRes = DiagnoseReadRes.builder()
                    .id(diagnose.getId())
                    .title(diagnose.getTitle())
                    .description(diagnose.getDescription())
                    .iconUrl(diagnose.getIconUrl())
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
    public void submitTestAll(String userLoginId, AnswerWriteReq answerWriteReq) {
        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        Answer answer = Answer.builder()
                .player(player)
                .build();

        Answer savedAnswer = answerRepository.save(answer);

        player.addAnswer(savedAnswer);

        for (DiagnoseWriteReq diagnoseWriteReq : answerWriteReq.getAnswerWriteReq()) {
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
     * (다수 선택) 유형 진단 질문 리스트 추출
     */
    public List<DiagnoseReadRes> getMultiTestDiagnoseRead(List<Long> ids) {
        List<Diagnose> diagnoseList = diagnoseRepository.findAllById(ids);

        List<DiagnoseReadRes> result = new ArrayList<>();

        for (Diagnose diagnose : diagnoseList) {
            List<Question> questionList = questionRepository.findAllByDiagnoseIdAndDeletedFalse(diagnose.getId());

            List<QuestionReadRes> questionReadResList = new ArrayList<>();
            for (Question question : questionList) {

                List<QuestionDetailReadRes> questionDetailReadResList = new ArrayList<>();
                for (QuestionDetail questionDetail : question.getQuestionDetailList()){
                    questionDetailReadResList.add(questionDetail.toQuestionDetailReadRes());
                }
                QuestionReadRes questionReadRes = QuestionReadRes.builder()
                        .id(question.getId())
                        .context(question.getContext())
                        .questionDetailReadResList(questionDetailReadResList)
                        .build();

                questionReadResList.add(questionReadRes);
            }

            DiagnoseReadRes diagnoseReadRes = DiagnoseReadRes.builder()
                    .id(diagnose.getId())
                    .title(diagnose.getTitle())
                    .description(diagnose.getDescription())
                    .iconUrl(diagnose.getIconUrl())
                    .questionReadResList(questionReadResList)
                    .build();

            result.add(diagnoseReadRes);
        }


        return result;
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
     * 유형진단 답변 제출
     * 최근 Answer 에 덮어 씌우기
     */
    @Transactional
    public void submitTestType(String userLoginId, AnswerWriteReq answerWriteReq, AnswerDiagnoseWriteReq answerDiagnoseWriteReq) {
        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        Answer answer = Answer.builder()
                .player(player)
                .build();

        Answer savedAnswer = answerRepository.save(answer);

        player.addAnswer(savedAnswer);

        for (DiagnoseWriteReq diagnoseWriteReq : answerWriteReq.getAnswerWriteReq()) {
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

        for (DiagnoseAvgWriteReq diagnoseAvgWriteReq : answerDiagnoseWriteReq.getAnswerDiagnoseWriteReq()) {
            Diagnose diagnose = diagnoseRepository.findById(diagnoseAvgWriteReq.getId()).get();
            AnswerDiagnose answerDiagnose = AnswerDiagnose.builder()
                    .answer(savedAnswer)
                    .diagnose(diagnose).build();

            AnswerDiagnose savedAnswerDiagnose = answerDiagnoseRepository.save(answerDiagnose);
            savedAnswer.addAnswerDiagnose(savedAnswerDiagnose);

            List<AnswerDetail> answerDetailList = new ArrayList<>();

            for (Question question : questionRepository.findAllByDiagnoseIdAndDeletedFalse(diagnose.getId())) {
                AnswerDetail answerDetail = AnswerDetail.builder()
                        .question(question)
                        .answerDiagnose(savedAnswerDiagnose)
                        .answer(diagnoseAvgWriteReq.getAvg()).build();

                answerDetailList.add(answerDetail);
            }

            List<AnswerDetail> savedAnswerDetailList = answerDetailRepository.saveAll(answerDetailList);
            savedAnswerDiagnose.addAnswerDetailList(savedAnswerDetailList);
        }
    }

    /**
     * 진단 결과 (목록)
     */
    @Transactional(readOnly = true)
    public List<TestResultInfoReadRes> getAllTestResultByUserLoginId(String userLoginId) {
        Long userId = userRepository.findUserIdByLoginId(userLoginId);
        List<Answer> answerList = answerRepository.findAnswersByPlayerUserIdOrderByUpdatedAt(userId);

        List<TestResultInfoReadRes> result = new ArrayList<>();
        answerList.forEach(
                answer -> {
                    result.add(
                            TestResultInfoReadRes.builder()
                                    .id(answer.getId())
                                    .date(answer.getUpdatedAt().toLocalDate())
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
    public TestResultPlayerInfoReadRes getPlayerInfoOfTestResult(String userLoginId) {
        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

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

        return TestResultPlayerInfoReadRes.builder()
                .name(player.getUser().getName())
                .age(player.getUser().getAge())
                .position(player.getPosition())
                .teamName(player.getUser().getTeam().getName())
                .nextMatchDDay(nextMatchDDay.toString())
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


    public List<DiagnoseInfoReadRes> getDiagnoseInfoReadExceptByIds(List<Long> ids) {
        List<Diagnose> diagnoseList = diagnoseRepository.findAllByDeletedFalse();

        List<DiagnoseInfoReadRes> result = new ArrayList<>();

        for (Diagnose diagnose : diagnoseList) {
            if (!ids.contains(diagnose.getId())) {
                DiagnoseInfoReadRes diagnoseInfoReadRes = DiagnoseInfoReadRes.builder()
                        .id(diagnose.getId())
                        .title(diagnose.getTitle())
                        .iconUrl(diagnose.getIconUrl())
                        .build();
                result.add(diagnoseInfoReadRes);
            }
        }

        return result;
    }

    public List<TestDiagnoseResultReadRes> getPositionResult(String userLoginId) {
        String position = playerRepository.findPlayerPositionByUserLogin_id(userLoginId);

        return playerRepository.findTestDiagnoseResultByPosition(position);
    }

    public List<TestDiagnoseResultReadRes> getAgeResult(String userLoginId) {
        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        return playerRepository.findTestDiagnoseResultByAge(player.getUser().getAge());
    }

    public List<MonthlyResultReadRes> getMonthlyResult(String userLoginId, LocalDate date) {
        List<MonthlyResultReadRes> result = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            YearMonth yearMonth = YearMonth.from(date.minusMonths(i)); // 현재 날짜부터 i달 전의 YearMonth 객체
            String formattedYearMonth = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")); // YearMonth를 원하는 형식으로 포맷

            MonthlyResultReadRes monthlyResultReadRes = MonthlyResultReadRes.builder()
                    .yearMonth(formattedYearMonth)
                    .diagnoseResultList(playerRepository.findTestDiagnoseResultByUserLoginIdAndYearMonth(userLoginId, formattedYearMonth))
                    .build();

            result.add(monthlyResultReadRes);
        }

        return result;
    }
}
