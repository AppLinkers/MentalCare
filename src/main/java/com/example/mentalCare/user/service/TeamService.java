package com.example.mentalCare.user.service;

import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.type.Role;
import com.example.mentalCare.user.dto.GetProfileRes;
import com.example.mentalCare.user.dto.UpdatePlayerRoleReq;
import com.example.mentalCare.user.repository.PlayerRepository;
import com.example.mentalCare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final UserAuthService userAuthService;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    public List<GetProfileRes> getPlayerProfileResListByTeamId(Long teamId) {
        List<String> userLoginIdList = userRepository.findUserLoginIdByTeamId(teamId);

        List<GetProfileRes> result = new ArrayList<>();
        userLoginIdList.forEach(
                userLoginId -> {
                    result.add(userAuthService.getProfile(userLoginId, Role.PLAYER.toString()));
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
