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
     * List<Diagnose> -> List<GetDiagnoseRes>
     */
    /*
    public List<GetDiagnoseRes> getDiagnoseData(List<Diagnose> diagnoseList){
        List<GetDiagnoseRes> result = new ArrayList<>();

        for(Diagnose diagnose : diagnoseList){
            int sum =0;
            for(Question q : diagnose.getQuestionList()){
                sum+=q.getWeight();
            }
            result.add(new GetDiagnoseRes(diagnose.getId(),
                    diagnose.getDiagnoseTitle(),
                    diagnose.getQuestionList(),
                    sum/diagnose.getQuestionList().size(),
                    diagnose.getUserId()));
        }
        return result;
    }*/

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
    /*
    public GetTestRes getTestById(Long id){
        Test test = testRepository.findByTestId(id);

        return GetTestRes.builder()
                .test_id(test.getTest_id())
                .date(test.getDate())
                .user_id(test.getUser_id())
                .diagnoseList(getDiagnoseData(test.getDiagnoseList()))
                .build();
    }*/

    /**
     * 해당 진단 결과 계산
     */
    /*
    public void calcQuestion(Diagnose diagnose){
        int result=0;
        for(int i=0; i<diagnose.getQuestionList().size(); i++){
            result+=diagnose.getQuestionList().get(i).getWeight();
        }
    }*/


    /**
     * 진단 작성
     */
    @Transactional
    public void submitAnswer(WriteAnswerReq req) {
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
    /*
    public void writeTest(WriteTestReq req, BuildDiagnoseReq buildDiagnoseReq){
        Test testForm = testRepository.findByTestId(0L);

        Test test = Test.builder()
                .user_id(req.getUser_id())
                .date(req.getDate())
                .build();

        testRepository.save(test);

        for(int i=0; i<testForm.getDiagnoseList().size(); i++){
            Diagnose diagnose = Diagnose.builder()
                    .diagnoseTitle(testForm.getDiagnoseList().get(i).getDiagnoseTitle())
                    .test(test)
                    .userId("asdf")
                    .build();
            diagnoseRepository.save(diagnose);

            for(int j=0; j<testForm.getDiagnoseList().get(i).getQuestionList().size(); j++){
                String questionContext = testForm.getDiagnoseList().get(i).getQuestionList().get(j).getQuestionContext();
                List<AnswerQuestion> answerList = buildDiagnoseReq.getAnswerQuestions();
                for(int a=0; a<answerList.size(); a++){
                    if(answerList.get(a).getQuestionContext().equals(questionContext)){
                        Question question = new Question();
                        question.setKeyWord(testForm.getDiagnoseList().get(i).getQuestionList().get(j).getKeyWord());
                        question.setQuestionContext(testForm.getDiagnoseList().get(i).getQuestionList().get(j).getQuestionContext());
                        question.setWeight(answerList.get(a).getWeight());
                        question.setDiagnose(diagnose);
                        questionRepository.save(question);
                    }
                }
            }
        }

    }*/

    /**
     * 해당 사용자의 모든 진단 결과 불러오기
     */
    @Transactional(readOnly = true)
    public List<GetAnswerInfoRes> getAllAnswerByUserLoginId(String userLoginId) {
        List<Answer> answerList = answerRepository.findAnswersByPlayerUserLoginId(userLoginId);

        List<GetAnswerInfoRes> answerResList = new ArrayList<>();
        answerList.forEach(
                answer -> {
                    answerResList.add(
                            GetAnswerInfoRes.builder()
                                    .answerId(answer.getId())
                                    .answerDate(answer.getCreatedAt())
                                    .build()
                    );
                }
        );

        return answerResList;
    }
    /*
    public List<GetTestRes> getAllTestByUserId(String userId){
        List<GetTestRes> result = new ArrayList<>();
        Optional<List<Test>> testList = testRepository.findAllTestByUserId(userId);

        if(testList.isPresent()){
            testList.get().forEach(
                    test -> {
                        result.add(
                                GetTestRes.builder()
                                        .test_id(test.getTest_id())
                                        .user_id(test.getUser_id())
                                        .date(test.getDate())
                                        .diagnoseList(getDiagnoseData(test.getDiagnoseList()))
                                        .build()
                        );
                    }
            );
        }
        return result;
    }*/

    /**
     * 통합검사 질문 리스트 추출
     */
    @Transactional(readOnly = true)
    public List<GetDiagnoseRes> getDiagnoseResList() {
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


}
