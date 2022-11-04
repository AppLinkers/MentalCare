package com.example.mentalCare.user.repository;

import com.example.mentalCare.user.domain.Player;
import com.example.mentalCare.user.domain.type.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("select p.position from Player p where p.user.id = :userId")
    Position findPlayerPositionByUserId(Long userId);

    @Query("select p from Player p where p.user.login_id = :loginId")
    Player findPlayerByUserLoginId(String loginId);

}