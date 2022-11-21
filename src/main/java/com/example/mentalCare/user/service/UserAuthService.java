package com.example.mentalCare.user.service;

import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.domain.type.Role;
import com.example.mentalCare.user.dto.GetProfileRes;
import com.example.mentalCare.user.dto.UpdatePlayerProfileReq;
import com.example.mentalCare.user.dto.UpdatePlayerRoleReq;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import com.example.mentalCare.team.repository.TeamRepository;
import com.example.mentalCare.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;


@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public GetProfileRes getProfile(String userLoginId, String authority) {
        if (authority.equals(Role.PLAYER.toString())) {
            Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

            int nextMatchDDay = 0;

            if (player.getNextMatch() != null) {
                nextMatchDDay = (int)DAYS.between(player.getNextMatch(), LocalDate.now());
            }

            return GetProfileRes.playerBuilder()
                    .id(player.getUser().getId())
                    .userName(player.getUser().getName())
                    .imgUrl(player.getUser().getImgUrl())
                    .role(Role.PLAYER)
                    .teamName(player.getUser().getTeam().getName())
                    .teamCode(player.getUser().getTeam().getCode())
                    .nextMatchDate(player.getNextMatch())
                    .nextMatchDDay(nextMatchDDay)
                    .position(player.getPosition())
                    .build();
        } else {
            User user = userRepository.findUserByLoginId(userLoginId).get();
            return GetProfileRes.directorBuilder()
                    .userName(user.getName())
                    .imgUrl(user.getImgUrl())
                    .role(Role.valueOf(authority))
                    .teamName(user.getTeam().getName())
                    .teamCode(user.getTeam().getCode())
                    .build();
        }

    }

    @Transactional
    public void updatePlayerProfile(String userLoginId,UpdatePlayerProfileReq updatePlayerProfileReq){
        Team team = teamRepository.findTeamByCode(updatePlayerProfileReq.getTeamCode()).get();

        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        player.setPosition(updatePlayerProfileReq.getPosition());
        player.getUser().setTeam(team);
        player.setNextMatch(updatePlayerProfileReq.getNextMatchDate());
    }

    @Transactional
    public void updatePlayerRole(UpdatePlayerRoleReq updatePlayerRoleReq) {

        User user = playerRepository.findById(updatePlayerRoleReq.getPlayerId()).get().getUser();

        if (!user.getRole().equals(updatePlayerRoleReq.getRole())) {
            user.setRole(updatePlayerRoleReq.getRole());
        }
    }

    public Team getTeamByUserLoginId(String userLoginId) {
        User user = userRepository.findUserByLoginId(userLoginId).get();
        return user.getTeam();
    }



}
