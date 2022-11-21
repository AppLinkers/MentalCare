package com.example.mentalCare.director.profile.service;

import com.example.mentalCare.director.profile.domain.Director;
import com.example.mentalCare.director.profile.dto.DirectorProfileReadRes;
import com.example.mentalCare.director.profile.dto.DirectorProfileUpdateReq;
import com.example.mentalCare.director.profile.dto.DirectorProfileUpdateRes;
import com.example.mentalCare.director.profile.repository.DirectorRepository;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.dto.PlayerProfileUpdateReq;
import com.example.mentalCare.player.profile.dto.PlayerProfileUpdateRes;
import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectorProfileService {

    private final DirectorRepository directorRepository;
    private final TeamRepository teamRepository;

    /**
     * 선수 프로필 조회 페이지
     */
    @Transactional(readOnly = true)
    public DirectorProfileReadRes getProfileRead(String userLoginId) {
        Director director = directorRepository.findDirectorByUserLogin_id(userLoginId);

        return DirectorProfileReadRes.builder()
                .id(director.getUser().getId())
                .name(director.getUser().getName())
                .imgUrl(director.getUser().getImgUrl())
                .role(director.getUser().getRole())
                .teamName(director.getUser().getTeam().getName())
                .build();
    }

    /**
     * 선수 프로필 설정 페이지
     */
    @Transactional(readOnly = true)
    public DirectorProfileUpdateRes getProfileUpdate(String userLoginId) {

        Director director = directorRepository.findDirectorByUserLogin_id(userLoginId);

        return DirectorProfileUpdateRes.builder()
                .id(director.getUser().getId())
                .name(director.getUser().getName())
                .imgUrl(director.getUser().getImgUrl())
                .role(director.getUser().getRole())
                .teamCode(director.getUser().getTeam().getCode())
                .build();
    }

    /**
     * 선수 프로필 설정 서비스
     */
    @Transactional
    public void UpdateProfile(String userLoginId, DirectorProfileUpdateReq request) {

        Director director = directorRepository.findDirectorByUserLogin_id(userLoginId);

        Team team = teamRepository.findTeamByCode(request.getTeamCode()).get();

        director.getUser().setTeam(team);
        // 이미지 설정 필요
    }

}
