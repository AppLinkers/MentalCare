package com.example.mentalCare.user.service;

import com.example.mentalCare.user.domain.Director;
import com.example.mentalCare.user.domain.TeamNotification;
import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.dto.*;
import com.example.mentalCare.user.repository.DirectorRepository;
import com.example.mentalCare.user.repository.TeamNotificationRepository;
import com.example.mentalCare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final UserAuthService userAuthService;
    private final PlayerService playerService;
    private final UserRepository userRepository;
    private final DirectorRepository directorRepository;
    private final TeamNotificationRepository teamNotificationRepository;


    public List<GetPlayerInfoRes> getPlayerOrPendingListByTeamId(Long teamId) {
        List<User> userPlayerList = userRepository.findPlayerOrPendingUserByTeamId(teamId);

        List<GetPlayerInfoRes> result = new ArrayList<>();
        userPlayerList.forEach(
                userPlayer -> {
                    result.add(playerService.userToGetPlayerInfoRes(userPlayer));
                }
        );

        return result;
    }

    public List<String> getPlayerUserLoginIdListByTeamId(Long teamId) {
        return userRepository.findPlayerUserLoginIdByTeamId(teamId);
    }

    /**
     * 공지사항 정보 전체 조회
     */
    public List<GetTeamNotificationInfoRes> getTeamNotificationInfoResListByTeamId(Long teamId) {
        List<GetTeamNotificationInfoRes> result = new ArrayList<>();

        teamNotificationRepository.findTeamNotificationsByTeamId(teamId).forEach(
                teamNotification -> {
                    result.add(
                            GetTeamNotificationInfoRes.builder()
                                    .id(teamNotification.getId())
                                    .title(teamNotification.getTitle())
                                    .directorName(teamNotification.getDirector().getUser().getName())
                                    .viewCnt(teamNotification.getView_cnt())
                                    .createdAt(teamNotification.getCreatedAt())
                                    .build()
                    );
                }
        );

        return result;
    }

    /**
     * 공지사항 단건 조회
     */
    public GetTeamNotificationDetailRes getTeamNotificationDetailResByTeamNotificationId(Long teamNotificationId) {
        TeamNotification teamNotification = teamNotificationRepository.findById(teamNotificationId).get();

        return GetTeamNotificationDetailRes.builder()
                .id(teamNotification.getId())
                .title(teamNotification.getTitle())
                .content(teamNotification.getContent())
                .directorName(teamNotification.getDirector().getUser().getName())
                .viewCnt(teamNotification.getView_cnt())
                .createdAt(teamNotification.getCreatedAt())
                .build();
    }

    /**
     * 공지사항 작성
     */
    public void writeTeamNotification(WriteTeamNotificationReq writeTeamNotificationReq) {
        Director director = directorRepository.findDirectorByUserLogin_id(writeTeamNotificationReq.getUserLoginId());

        TeamNotification teamNotification = TeamNotification.builder()
                .title(writeTeamNotificationReq.getTitle())
                .content(writeTeamNotificationReq.getContent())
                .director(director)
                .team(director.getUser().getTeam())
                .build();

        teamNotificationRepository.save(teamNotification);
    }


}
