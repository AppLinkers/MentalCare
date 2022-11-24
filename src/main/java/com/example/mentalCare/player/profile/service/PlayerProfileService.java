package com.example.mentalCare.player.profile.service;

import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.dto.PlayerProfileReadRes;
import com.example.mentalCare.player.profile.dto.PlayerProfileUpdateReq;
import com.example.mentalCare.player.profile.dto.PlayerProfileUpdateRes;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class PlayerProfileService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    /**
     * 선수 프로필 조회 페이지
     */
    @Transactional(readOnly = true)
    public PlayerProfileReadRes getProfileRead(String userLoginId) {
        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        int nextMatchDDay = 0;

        if (player.getNextMatch() != null) {
            nextMatchDDay = (int) DAYS.between(player.getNextMatch(), LocalDate.now());
        }

        return PlayerProfileReadRes.builder()
                .id(player.getUser().getId())
                .name(player.getUser().getName())
                .imgUrl(player.getUser().getImgUrl())
                .role(player.getUser().getRole())
                .teamName(player.getUser().getTeam().getName())
                .nextMatchDDay(nextMatchDDay)
                .position(player.getPosition())
                .build();
    }

    /**
     * 선수 프로필 설정 페이지
     */
    @Transactional(readOnly = true)
    public PlayerProfileUpdateRes getProfileUpdate(String userLoginId) {

        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        return PlayerProfileUpdateRes.builder()
                .id(player.getUser().getId())
                .name(player.getUser().getName())
                .imgUrl(player.getUser().getImgUrl())
                .role(player.getUser().getRole())
                .teamCode(player.getUser().getTeam().getCode())
                .nextMatchDate(player.getNextMatch())
                .position(player.getPosition())
                .build();
    }

    /**
     * 선수 프로필 설정 서비스
     */
    @Transactional
    public void UpdateProfile(String userLoginId, PlayerProfileUpdateReq request) {

        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        Team team = teamRepository.findTeamByCode(request.getTeamCode()).get();

        player.getUser().setTeam(team);
        player.setPosition(request.getPosition());
        player.setNextMatch(request.getNextMatchDate());
        // 이미지 설정 필요
    }
}
