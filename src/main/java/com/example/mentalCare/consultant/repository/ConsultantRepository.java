package com.example.mentalCare.consultant.repository;

import com.example.mentalCare.consultant.domain.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<Consultant, Long> {
}
