package com.example.mentalCare.user.repository;

import com.example.mentalCare.user.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}