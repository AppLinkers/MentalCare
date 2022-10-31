package com.example.mentalCare.user.service;

import com.example.mentalCare.user.domain.*;
import com.example.mentalCare.user.domain.type.Role;
import com.example.mentalCare.user.dto.*;
import com.example.mentalCare.user.repository.DirectorRepository;
import com.example.mentalCare.user.repository.PlayerRepository;
import com.example.mentalCare.user.repository.TeamRepository;
import com.example.mentalCare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final DirectorRepository directorRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLoginId(username).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return UserDetail.builder()
                .username(user.getLogin_id())
                .password(user.getLogin_pw())
                .authorityList(roles)
                .build();
    }

    public User findUserByUserId(String userId) {
        User user = userRepository.findUserByLoginId(userId).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
        return user;
    }

    @Transactional
    public GetUserInfoRes signUp(PlayerSignUpReq request) {
        validateDuplicated(request.getLogin_id());

        User user = userSignUpReqToUser(request, Role.PENDING);
        user.setLogin_pw(passwordEncoder.encode(user.getLogin_pw()));

        User savedUser = userRepository.save(user);

        Player player = Player.builder()
                .user(savedUser)
                .position(request.getPosition())
                .build();

        if (request.getNextMatch() != null) {
            player.setNextMatch(request.getNextMatch());
        }

        playerRepository.save(player);


        return GetUserInfoRes.builder()
                .id(savedUser.getId())
                .login_id(savedUser.getLogin_id())
                .build();

    }

    @Transactional
    public GetUserInfoRes signUp(DirectorSignUpReq request) {
        validateDuplicated(request.getLogin_id());

        User user = userSignUpReqToUser(request, Role.DIRECTOR);
        user.setLogin_pw(passwordEncoder.encode(user.getLogin_pw()));

        User savedUser = userRepository.save(user);

        Director director = Director.builder()
                .user(savedUser)
                .build();

        directorRepository.save(director);


        return GetUserInfoRes.builder()
                .id(savedUser.getId())
                .login_id(savedUser.getLogin_id())
                .build();

    }

    public void validateDuplicated(String loginId) {
        userRepository.findUserByLoginId(loginId).ifPresent(
                (user) -> {
                    throw new RuntimeException("이미 존재하는 아이디 입니다.");
                }
        );
    }

    public GetPlayerProfileRes getPlayerProfile(String userLoginId){
        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        int nextMatchDDay = 0;

        if (player.getNextMatch() != null) {
            nextMatchDDay = (int)DAYS.between(player.getNextMatch(), LocalDate.now());
        }

        return GetPlayerProfileRes.builder()
                .userName(player.getUser().getName())
                .role(player.getUser().getRole())
                .position(player.getPosition())
                .nextMatchDate(player.getNextMatch())
                .nextMatchDDay(nextMatchDDay)
                .teamName(player.getUser().getTeam().getName())
                .build();
    }

    @Transactional
    public void updateProfile(String userLoginId,UpdatePlayerProfileReq updatePlayerProfileReq){
        Team team = teamRepository.findTeamByCode(updatePlayerProfileReq.getTeamCode()).get();

        Player player = playerRepository.findPlayerByUserLoginId(userLoginId);

        player.setPosition(updatePlayerProfileReq.getPosition());
        player.getUser().setTeam(team);
        player.setNextMatch(updatePlayerProfileReq.getNextMatchDate());
    }

    public Team getTeamByUserLoginId(String userLoginId) {
        User user = userRepository.findUserByLoginId(userLoginId).get();
        return user.getTeam();
    }


    public User userSignUpReqToUser(UserSignUpReq request, Role role) {
        Team team = teamRepository.findTeamByCode(request.getTeamCode()).get();
        return User.builder()
                .login_id(request.getLogin_id())
                .login_pw(request.getLogin_pw())
                .name(request.getName())
                .team(team)
                .age(request.getAge())
                .role(role)
                .build();
    }

}
