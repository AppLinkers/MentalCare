package com.example.mentalCare.test.repository;

import com.example.mentalCare.test.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}