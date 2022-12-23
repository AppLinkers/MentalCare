package com.example.mentalCare.director.profile.service;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.common.service.S3Service;
import com.example.mentalCare.director.profile.dto.DirectorProfileReadRes;
import com.example.mentalCare.director.profile.dto.DirectorProfileUpdateReq;
import com.example.mentalCare.director.profile.dto.DirectorProfileUpdateRes;
import com.example.mentalCare.director.profile.repository.DirectorRepository;
import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DirectorProfileService {

    private final DirectorRepository directorRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    private final S3Service s3Service;

    /**
     * 감독 프로필 조회 페이지
     */
    @Transactional(readOnly = true)
    public DirectorProfileReadRes getProfileRead(String userLoginId) {
        User user = userRepository.findUserByLoginId(userLoginId).get();

        return DirectorProfileReadRes.builder()
                .id(user.getId())
                .name(user.getName())
                .imgUrl(user.getImgUrl())
                .role(user.getRole())
                .teamName(user.getTeam().getName())
                .build();
    }

    /**
     * 감독 프로필 설정 페이지
     */
    @Transactional(readOnly = true)
    public DirectorProfileUpdateRes getProfileUpdate(String userLoginId) {
        User user = userRepository.findUserByLoginId(userLoginId).get();

        return DirectorProfileUpdateRes.builder()
                .id(user.getId())
                .name(user.getName())
                .imgUrl(user.getImgUrl())
                .role(user.getRole())
                .teamCode(user.getTeam().getCode())
                .build();
    }

    /**
     * 감독 프로필 설정 서비스
     */
    @Transactional
    public void UpdateProfile(String userLoginId, DirectorProfileUpdateReq request) throws IOException {
        User user = userRepository.findUserByLoginId(userLoginId).get();

        Team team = teamRepository.findTeamByCode(request.getTeamCode()).get();

        user.setTeam(team);
        // 이미지 설정
        if (!request.getImgFile().isEmpty()) {
            String imgUrl = s3Service.upload(request.getImgFile(), "director/profile");
            user.setImgUrl(imgUrl);
        }
    }


}
