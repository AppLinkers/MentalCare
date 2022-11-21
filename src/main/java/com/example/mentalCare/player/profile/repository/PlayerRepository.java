package com.example.mentalCare.player.profile.repository;

import com.example.mentalCare.player.profile.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("select p.position from Player p where p.user.id = :userId")
    String findPlayerPositionByUserId(Long userId);

    @Query("select p from Player p where p.user.login_id = :loginId")
    Player findPlayerByUserLoginId(String loginId);

}