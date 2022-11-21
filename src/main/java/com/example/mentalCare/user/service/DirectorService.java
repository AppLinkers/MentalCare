package com.example.mentalCare.user.service;

import com.example.mentalCare.team.domain.TeamNotification;
import com.example.mentalCare.team.repository.TeamNotificationRepository;
import com.example.mentalCare.director.profile.domain.Director;
import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.user.dto.GetPlayerInfoRes;
import com.example.mentalCare.user.dto.WriteTeamNotificationReq;
import com.example.mentalCare.director.profile.repository.DirectorRepository;
import com.example.mentalCare.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {

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
