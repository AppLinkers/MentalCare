package com.example.mentalCare.director.profile.repository;

import com.example.mentalCare.director.profile.domain.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DirectorRepository extends JpaRepository<Director, Long> {

    @Query("select d from Director d where d.user.login_id = :loginId")
    Director findDirectorByUserLogin_id(String loginId);

}