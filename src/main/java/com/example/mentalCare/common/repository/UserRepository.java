package com.example.mentalCare.common.repository;

import com.example.mentalCare.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.login_id = :loginId")
    Optional<User> findUserByLoginId(String loginId);

    @Query("select u.id from User u where u.login_id = :loginId")
    Long findUserIdByLoginId(String loginId);
}