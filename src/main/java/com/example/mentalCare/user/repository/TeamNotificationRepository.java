package com.example.mentalCare.user.repository;

import com.example.mentalCare.user.domain.TeamNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamNotificationRepository extends JpaRepository<TeamNotification, Long> {

    @Query("select tn from TeamNotification tn where tn.team.id = :teamId ")
    List<TeamNotification> findTeamNotificationsByTeamId(Long teamId);
}
