package com.example.mentalCare.user.service;


import com.example.mentalCare.user.domain.Diagnose.Diagnose;
import com.example.mentalCare.user.domain.Diagnose.Question;
import com.example.mentalCare.user.domain.Diagnose.Test;
import com.example.mentalCare.user.dto.*;
import com.example.mentalCare.user.repository.DiagnoseRepository;
import com.example.mentalCare.user.repository.QuestionRepository;
import com.example.mentalCare.user.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiagnoseService {

    private final DiagnoseRepository diagnoseRepository;
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

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
    }


    public Page<GetDiagnoseRes> getAllDiagnose(Pageable pageable){
        Page<Diagnose> findDiagnoseList = diagnoseRepository.findAll(pageable);


        return findDiagnoseList.map(diagnose ->
                new GetDiagnoseRes(
                        diagnose.getId(),
                        diagnose.getDiagnoseTitle(),
                        diagnose.getQuestionList(),
                        0,
                        diagnose.getUserId()
                ));
    }


    public GetTestRes getTestById(Long id){
        Test test = testRepository.findByTestId(id);

        return GetTestRes.builder()
                .test_id(test.getTest_id())
                .date(test.getDate())
                .user_id(test.getUser_id())
                .diagnoseList(getDiagnoseData(test.getDiagnoseList()))
                .build();
    }

    public void calcQuestion(Diagnose diagnose){
        int result=0;
        for(int i=0; i<diagnose.getQuestionList().size(); i++){
            result+=diagnose.getQuestionList().get(i).getWeight();
        }
    }


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
                        question.setQuestionContext(testForm.getDiagnoseList().get(i).getQuestionList().get(j).getQuestionContext());
                        question.setWeight(answerList.get(a).getWeight());
                        question.setDiagnose(diagnose);
                        questionRepository.save(question);
                    }
                }
            }
        }

    }

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
    }




}
