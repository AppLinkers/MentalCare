package com.example.mentalCare.user.dto;

import com.example.mentalCare.user.domain.Diagnose.Question;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.Transient;
import java.util.List;

@Data
public class BuildDiagnoseReq {


    List<AnswerQuestion> answerQuestions;

    public BuildDiagnoseReq(){

    }

    public void addAnswerQ(AnswerQuestion answerQuestion){
        this.answerQuestions.add(answerQuestion);
    }
    
}
