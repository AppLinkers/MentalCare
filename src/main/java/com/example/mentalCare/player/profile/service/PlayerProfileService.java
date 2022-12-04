package com.example.mentalCare.player.profile.service;

import com.example.mentalCare.common.service.S3Service;
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

import java.io.IOException;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class PlayerProfileService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    private final S3Service s3Service;

    /**
     * 선수 프로필 조회 페이지
     */
    @Transactional(readOnly = true)
    public PlayerProfileReadRes getProfileRead(String userLoginId) {
        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        StringBuilder nextMatchDDay = new StringBuilder();

        if (player.getNextMatch() != null) {
            Integer nextMatchDDayNum = (int) DAYS.between(player.getNextMatch(), LocalDate.now());

            if (nextMatchDDayNum >= 0) {
                nextMatchDDay.append("+ ");
            } else {
                nextMatchDDay.append("- ");
                nextMatchDDayNum *= -1;
            }
            nextMatchDDay.append(nextMatchDDayNum);
        }

        return PlayerProfileReadRes.builder()
                .id(player.getUser().getId())
                .name(player.getUser().getName())
                .imgUrl(player.getUser().getImgUrl())
                .role(player.getUser().getRole())
                .teamName(player.getUser().getTeam().getName())
                .nextMatchDDay(nextMatchDDay.toString())
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
    public void UpdateProfile(String userLoginId, PlayerProfileUpdateReq request) throws IOException {

        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        Team team = teamRepository.findTeamByCode(request.getTeamCode()).get();

        player.getUser().setTeam(team);
        player.setPosition(request.getPosition());
        player.setNextMatch(request.getNextMatchDate());
        // 이미지 설정
        if (!request.getImgFile().isEmpty()) {
            String imgUrl = s3Service.upload(request.getImgFile(), "mental_care/player/profile");
            player.getUser().setImgUrl(imgUrl);
        }
    }
}
