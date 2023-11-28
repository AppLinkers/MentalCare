package com.example.mentalCare.player.consulting.repository;

import com.example.mentalCare.consultant.profile.domain.Consultant;
import com.example.mentalCare.player.consulting.domain.RequestConsulting;
import com.example.mentalCare.player.profile.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestConsultingRepository extends JpaRepository<RequestConsulting, Long> {
    @Query("select rq from RequestConsulting rq where rq.consultant = :consultant and rq.player.user.login_id = :userLoginId")
    Optional<RequestConsulting> findByConsultantAndPlayerUserLogin_id(Consultant consultant, String userLoginId);

    @Query("select rq from RequestConsulting rq where rq.consultant.user.login_id = :userLoginId")
    List<RequestConsulting> findByConsultantUserLogin_id(String userLoginId);

    void deleteByConsultantAndPlayer(Consultant consultant, Player player);

    void deleteAllByPlayer(Player player);
}
