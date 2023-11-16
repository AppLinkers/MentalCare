package com.example.mentalCare.consultant.profile.service;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.common.service.S3Service;
import com.example.mentalCare.consultant.profile.dto.ConsultantProfileReadRes;
import com.example.mentalCare.consultant.profile.dto.ConsultantProfileUpdateReq;
import com.example.mentalCare.consultant.profile.dto.ConsultantProfileUpdateRes;
import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ConsultantProfileService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    private final S3Service s3Service;

    /**
     * 상담가 프로필 조회 페이지
     */
    @Transactional(readOnly = true)
    public ConsultantProfileReadRes getProfileRead(String userLoginId) {
        User user = userRepository.findUserByLoginId(userLoginId).get();

        return ConsultantProfileReadRes.builder()
                .name(user.getName())
                .imgUrl(user.getImgUrl())
                .teamName(user.getTeam() == null ? null : user.getTeam().getName())
                .build();
    }

    /**
     * 상담가 프로필 설정 페이지
     */
    @Transactional(readOnly = true)
    public ConsultantProfileUpdateRes getProfileUpdate(String userLoginId) {
        User user = userRepository.findUserByLoginId(userLoginId).get();

        return ConsultantProfileUpdateRes.builder()
                .name(user.getName())
                .imgUrl(user.getImgUrl())
                .teamCode(user.getTeam() == null ? null : user.getTeam().getCode())
                .build();
    }

    /**
     * 상담가 프로필 설정 서비스
     */
    @Transactional
    public void updateProfile(String userLoginId, ConsultantProfileUpdateReq request) throws IOException {
        User user = userRepository.findUserByLoginId(userLoginId).get();

        Team team = teamRepository.findTeamByCode(request.getTeamCode()).get();

        user.setTeam(team);
        // 이미지 설정
        if (!request.getImgFile().isEmpty()) {
            String imgUrl = s3Service.upload(request.getImgFile(), "consultant/profile");
            user.setImgUrl(imgUrl);
        }
    }
}
