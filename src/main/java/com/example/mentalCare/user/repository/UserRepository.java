package com.example.mentalCare.user.repository;

import com.example.mentalCare.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.login_id = :login_id")
    Optional<User> findUserByLogin_id(String login_id);
}