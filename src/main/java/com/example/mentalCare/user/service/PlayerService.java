package com.example.mentalCare.user.service;

import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.user.dto.GetPlayerInfoRes;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public GetPlayerInfoRes userToGetPlayerInfoRes(User user) {
        Player player = playerRepository.findPlayerByUserLoginId(user.getLogin_id());

        return GetPlayerInfoRes.builder()
                .id(user.getId())
                .login_id(user.getLogin_id())
                .name(user.getName())
                .profileImg(user.getImgUrl())
                .role(user.getRole())
                .position(player.getPosition())
                .age(user.getAge())
                .build();
    }
}
