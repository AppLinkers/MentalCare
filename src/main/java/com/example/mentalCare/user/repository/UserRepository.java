package com.example.mentalCare.user.repository;

import com.example.mentalCare.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin_id(String login_id);
}