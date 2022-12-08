package com.example.mentalCare.director.profile.repository;

import com.example.mentalCare.director.profile.domain.Director;
import com.example.mentalCare.director.profile.dto.DirectorProfileReadRes;
import com.example.mentalCare.director.profile.dto.DirectorProfileUpdateRes;
import com.example.mentalCare.director.team.dto.DirectorRoleUpdateReq;
import com.example.mentalCare.player.profile.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DirectorRepository extends JpaRepository<Director, Long> {

    @Query("select d from Director d where d.user.login_id = :loginId")
    Director findDirectorByUserLogin_id(String loginId);

    @Query("select d from Director d where d.user.team.id = :teamId")
    List<Director> findDirectorByUserTeamId(Long teamId);

}