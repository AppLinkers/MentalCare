package com.example.mentalCare.diagnose.repository;

import com.example.mentalCare.diagnose.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q from Question q where q.diagnose.id = :diagnoseId and q.deleted = FALSE ")
    List<Question> findAllByDiagnoseIdAndDeletedFalse(Long diagnoseId);
}
