package com.example.mentalCare.user.service;

import com.example.mentalCare.user.domain.Player;
import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.dto.GetPlayerProfileRes;
import com.example.mentalCare.user.dto.UpdatePlayerRoleReq;
import com.example.mentalCare.user.repository.PlayerRepository;
import com.example.mentalCare.user.repository.TeamRepository;
import com.example.mentalCare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    public List<GetPlayerProfileRes> getPlayerProfileResListByTeamId(Long teamId) {
        List<Player> playerList = playerRepository.findPlayersByUserTeamId(teamId);

        List<GetPlayerProfileRes> result = new ArrayList<>();
        playerList.forEach(
                player -> {
                    int nextMatchDDay = 0;

                    if (player.getNextMatch() != null) {
                        nextMatchDDay = (int)DAYS.between(player.getNextMatch(), LocalDate.now());
                    }

                    GetPlayerProfileRes getPlayerProfileRes =  GetPlayerProfileRes.builder()
                            .userName(player.getUser().getName())
                            .role(player.getUser().getRole())
                            .position(player.getPosition())
                            .nextMatchDate(player.getNextMatch())
                            .nextMatchDDay(nextMatchDDay)
                            .teamName(player.getUser().getTeam().getName())
                            .build();

                    result.add(getPlayerProfileRes);
                }
        );

        return result;
    }

    public void updatePlayerRole(List<UpdatePlayerRoleReq> updatePlayerRoleReqList) {

        List<User> userList = new ArrayList<>();

        updatePlayerRoleReqList.forEach(
            updatePlayerRoleReq -> {
                User user = playerRepository.findById(updatePlayerRoleReq.getPlayerId()).get().getUser();

                if (!user.getRole().equals(updatePlayerRoleReq.getRole())) {
                    user.setRole(updatePlayerRoleReq.getRole());
                    userList.add(user);
                }
            }
        );

        userRepository.saveAll(userList);
    }
}
