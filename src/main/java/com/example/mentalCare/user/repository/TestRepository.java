package com.example.mentalCare.user.repository;

import com.example.mentalCare.user.domain.Diagnose.Test;
import com.example.mentalCare.user.dto.GetTestRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    @Query(value = "SELECT c FROM Test c WHERE c.user_id = :user_id")
    Optional<List<Test>> findAllTestByUserId(String user_id);

    @Query(value = "SELECT c FROM Test c WHERE c.test_id = :id")
    Test findByTestId(Long id);
}
