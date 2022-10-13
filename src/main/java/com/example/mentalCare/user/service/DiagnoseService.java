package com.example.mentalCare.user.service;


import com.example.mentalCare.user.domain.Diagnose.Diagnose;
import com.example.mentalCare.user.domain.Diagnose.Question;
import com.example.mentalCare.user.domain.Diagnose.Test;
import com.example.mentalCare.user.dto.BuildDiagnoseReq;
import com.example.mentalCare.user.dto.GetDiagnoseRes;
import com.example.mentalCare.user.dto.GetTestRes;
import com.example.mentalCare.user.repository.DiagnoseRepository;
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

    public void buildDiagnose(BuildDiagnoseReq request)throws IOException {
        Diagnose diagnose = Diagnose.builder()
                .diagnoseTitle(request.getDiagnoseTitle())
                .userId(request.getUserId())
                .build();
        diagnoseRepository.save(diagnose);
    }

    public Page<GetDiagnoseRes> getAllDiagnose(Pageable pageable){
        Page<Diagnose> findDiagnoseList = diagnoseRepository.findAll(pageable);

        return findDiagnoseList.map(diagnose ->
                new GetDiagnoseRes(
                        diagnose.getId(),
                        diagnose.getDiagnoseTitle(),
                        diagnose.getQuestionList(),
                        diagnose.getUserId()
                ));
    }

    public List<GetTestRes> getAllTestByUserId(String userId){
        List<GetTestRes> result = new ArrayList<>();
        Optional<List<Test>> testList = testRepository.findAllTestByUserId(userId);

        if(testList.isPresent()){
            testList.get().forEach(
                    test -> {
                        result.add(
                                GetTestRes.builder()
                                        .id(test.getId())
                                        .user_id(test.getUser_id())
                                        .date(test.getDate())
                                        .diagnoseList(test.getDiagnoseList())
                                        .build()
                        );
                    }
            );
        }
        return result;
    }




}
