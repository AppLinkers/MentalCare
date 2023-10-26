package com.example.mentalCare.director.profile.service;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.common.service.S3Service;
import com.example.mentalCare.director.profile.domain.Director;
import com.example.mentalCare.director.profile.dto.DirectorProfileReadRes;
import com.example.mentalCare.director.profile.dto.DirectorProfileUpdateReq;
import com.example.mentalCare.director.profile.dto.DirectorProfileUpdateRes;
import com.example.mentalCare.director.profile.repository.DirectorRepository;
import com.example.mentalCare.director.team.dto.TeamDiagnoseResultReadRes;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import com.example.mentalCare.player.test.domain.Answer;
import com.example.mentalCare.player.test.domain.AnswerDiagnose;
import com.example.mentalCare.player.test.repository.AnswerRepository;
import com.example.mentalCare.player.test.repository.DiagnoseRepository;
import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DirectorProfileService {

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
                .name(user.getName())
                .imgUrl(user.getImgUrl())
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
                .name(user.getName())
                .imgUrl(user.getImgUrl())
                .teamCode(user.getTeam().getCode())
                .build();
    }

    /**
     * 감독 프로필 설정 서비스
     */
    @Transactional
    public void updateProfile(String userLoginId, DirectorProfileUpdateReq request) throws IOException {
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
