package com.example.mentalCare.team.service;

import com.example.mentalCare.director.profile.domain.Director;
import com.example.mentalCare.director.profile.repository.DirectorRepository;
import com.example.mentalCare.director.team.dto.TeamNotificationWriteReq;
import com.example.mentalCare.team.domain.TeamNotification;
import com.example.mentalCare.team.dto.TeamNotificationDetailRes;
import com.example.mentalCare.team.dto.TeamNotificationInfoRes;
import com.example.mentalCare.team.repository.TeamNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final TeamNotificationRepository teamNotificationRepository;

    private final DirectorRepository directorRepository;

    /**
     * 공지사항 정보 전체 조회
     */
    @Transactional(readOnly = true)
    public List<TeamNotificationInfoRes> getTeamNotificationInfoResListByTeamId(Long teamId) {
        List<TeamNotificationInfoRes> result = new ArrayList<>();

        teamNotificationRepository.findTeamNotificationsByTeamId(teamId).forEach(
                teamNotification -> {
                    result.add(
                            TeamNotificationInfoRes.builder()
                                    .id(teamNotification.getId())
                                    .title(teamNotification.getTitle())
                                    .directorName(teamNotification.getDirector().getUser().getName())
                                    .createdAt(teamNotification.getCreatedAt().toLocalDate())
                                    .build()
                    );
                }
        );

        return result;
    }

    /**
     * 공지사항 단건 조회
     */
    @Transactional
    public TeamNotificationDetailRes getTeamNotificationDetailResByTeamNotificationId(Long teamNotificationId) {
        TeamNotification teamNotification = teamNotificationRepository.findById(teamNotificationId).get();
        teamNotification.increaseViewCnt();

        return TeamNotificationDetailRes.builder()
                .id(teamNotification.getId())
                .title(teamNotification.getTitle())
                .content(teamNotification.getContent())
                .directorName(teamNotification.getDirector().getUser().getName())
                .directorImgUrl(teamNotification.getDirector().getUser().getImgUrl())
                .viewCnt(teamNotification.getView_cnt())
                .createdAt(teamNotification.getCreatedAt().toLocalDate())
                .build();
    }

    /**
     * 팀 공지사항 작성 서비스
     */
    @Transactional
    public void notificationWrite(String userLoginId, TeamNotificationWriteReq teamNotificationWriteReq) {
        Director director = directorRepository.findDirectorByUserLogin_id(userLoginId);
        TeamNotification teamNotification = TeamNotification.builder()
                .team(director.getUser().getTeam())
                .director(director)
                .title(teamNotificationWriteReq.getTitle())
                .content(teamNotificationWriteReq.getContent())
                .build();

        teamNotificationRepository.save(teamNotification);
    }

}
