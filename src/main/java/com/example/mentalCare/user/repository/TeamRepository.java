package com.example.mentalCare.user.repository;

import com.example.mentalCare.user.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findTeamByCode(String code);
}