package com.example.mentalCare.diagnose.repository;

import com.example.mentalCare.diagnose.domain.Diagnose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {

    List<Diagnose> findAllByDeletedFalse();
}
