package com.example.mentalCare.diagnose.repository;

import com.example.mentalCare.diagnose.domain.AnswerDiagnose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerDiagnoseRepository extends JpaRepository<AnswerDiagnose, Long> {
}