package com.example.mentalCare.user.repository;

import com.example.mentalCare.user.domain.Diagnose.Diagnose;
import com.example.mentalCare.user.domain.Diagnose.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {


}
