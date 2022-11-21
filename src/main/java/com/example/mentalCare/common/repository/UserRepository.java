package com.example.mentalCare.common.repository;

import com.example.mentalCare.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.login_id = :loginId")
    Optional<User> findUserByLoginId(String loginId);

    @Query("select u from User u where u.team.id = :teamId and (u.role = 'PLAYER' or u.role = 'PENDING') ")
    List<User> findPlayerOrPendingUserByTeamId(Long teamId);

    @Query("select u.login_id from User u where u.team.id = :teamId and u.role = 'PLAYER'")
    List<String> findPlayerUserLoginIdByTeamId(Long teamId);
}