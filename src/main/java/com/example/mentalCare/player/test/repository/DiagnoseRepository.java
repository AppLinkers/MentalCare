package com.example.mentalCare.player.test.repository;

import com.example.mentalCare.player.test.domain.Diagnose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {

    List<Diagnose> findAllByDeletedFalse();

}
