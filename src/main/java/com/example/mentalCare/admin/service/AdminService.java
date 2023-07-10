package com.example.mentalCare.admin.service;

import com.example.mentalCare.admin.dto.*;
import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.domain.type.Role;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    /**
     * 사용자 정보 목록 추출
     */
    @Transactional(readOnly = true)
    public List<UserInfoRes> getUserInfoResList() {
        List<UserInfoRes> result = new ArrayList<>();

        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            if (user.getRole().equals(Role.ADMIN)) {
                continue;
            }

            result.add(
                    UserInfoRes.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .role(user.getRole())
                            .imgUrl(user.getImgUrl())
                            .teamName(user.getTeam().getName())
                            .teamCode(user.getTeam().getCode())
                            .build()
            );

        }
        return result;
    }

    /**
     * 사용자 권한 수정
     */
    @Transactional
    public void changeUserRoleList(UserRoleUpdateListReq userRoleUpdateListReq) {
        for (UserRoleUpdateReq userRoleUpdateReq : userRoleUpdateListReq.getUserRoleUpdateReqList()) {
            User user = userRepository.findById(userRoleUpdateReq.getId()).get();

            if (!user.getRole().equals(userRoleUpdateReq.getRole())) {
                user.setRole(userRoleUpdateReq.getRole());
            }
        }
    }

    /**
     * 팀 정보 목록 추출
     */
    @Transactional(readOnly = true)
    public List<TeamInfoRes> getTeamInfoResList() {
        List<TeamInfoRes> result = new ArrayList<>();

        List<Team> teamList = teamRepository.findAll();
        for (Team team : teamList) {
            result.add(
                    TeamInfoRes.builder()
                            .id(team.getId())
                            .name(team.getName())
                            .teamCode(team.getCode())
                            .build()
            );
        }

        return result;
    }

    /**
     * 팀 삭제
     */
    @Transactional
    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).get();

        if (team.getUserList().isEmpty()) {
            teamRepository.deleteById(teamId);
        } else {
            throw new RuntimeException("팀에 속해있는 사용자가 존재합니다.");
        }

    }

    /**
     * 팀 추가
     */
    @Transactional
    public void addTeam(TeamAddReq teamAddReq) {
        Team team = Team.builder()
                .name(teamAddReq.getName())
                .code(teamAddReq.getTeamCode())
                .build();

        teamRepository.save(team);
    }


    /**
     * 사용자 정보 목록 -> 사용자 권한 수정 정보 request
     */
    public UserRoleUpdateListReq userInfoListToUserRoleUpdateListReq(List<UserInfoRes> userInfoResList) {
        UserRoleUpdateListReq result = new UserRoleUpdateListReq();
        for (UserInfoRes userInfoRes : userInfoResList) {
            result.add(
                    UserRoleUpdateReq.builder()
                            .id(userInfoRes.getId())
                            .role(userInfoRes.getRole())
                            .build()
            );
        }

        return result;
    }
}
