package com.example.mentalCare.diagnose.repository;

import com.example.mentalCare.diagnose.domain.Diagnose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {

    List<Diagnose> findAllByDeletedFalse();
    Diagnose findByDeletedIsFalseAndId(Long id);
}
