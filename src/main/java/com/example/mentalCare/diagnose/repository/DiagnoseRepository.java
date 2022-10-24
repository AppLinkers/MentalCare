package com.example.mentalCare.diagnose.repository;

import com.example.mentalCare.diagnose.domain.Diagnose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {

    List<Diagnose> findAllByDeletedFalse();

    @Query("select d from Diagnose d where d.id = :diagnoseId and d.deleted = false")
    Diagnose findByIdAndAndDeletedFalse(Long diagnoseId);
}
