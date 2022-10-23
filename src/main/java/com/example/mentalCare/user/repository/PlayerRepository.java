package com.example.mentalCare.user.repository;

import com.example.mentalCare.user.domain.Player;
import com.example.mentalCare.user.domain.type.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("select p.position from Player p where p.user.id = :user_id")
    Position findPlayerPositionByUserId(Long user_id);

    @Query("select p from Player p where p.user.login_id = :login_id")
    Player findPlayerByUserLoginId(String login_id);
}