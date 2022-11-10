package com.example.mentalCare.diagnose.repository;

import com.example.mentalCare.diagnose.domain.AnswerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerDetailRepository extends JpaRepository<AnswerDetail, Long> {

}