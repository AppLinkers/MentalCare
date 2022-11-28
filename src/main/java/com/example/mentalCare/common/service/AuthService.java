package com.example.mentalCare.common.service;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.domain.UserDetail;
import com.example.mentalCare.common.dto.SignUpDirectorReq;
import com.example.mentalCare.common.dto.SignUpPlayerReq;
import com.example.mentalCare.common.dto.SignUpUserReq;
import com.example.mentalCare.director.profile.domain.Director;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.common.domain.type.Role;
import com.example.mentalCare.director.profile.repository.DirectorRepository;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import com.example.mentalCare.team.repository.TeamRepository;
import com.example.mentalCare.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final DirectorRepository directorRepository;
    private final TeamRepository teamRepository;

    /**
     * 로그인 아이디 -> 사용자 정보 조회
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLoginId(username).orElseThrow(() -> new UsernameNotFoundException("invalid ID"));
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return UserDetail.builder()
                .username(user.getLogin_id())
                .password(user.getLogin_pw())
                .authorityList(roles)
                .build();
    }

    /**
     * 선수 회원가입
     */
    @Transactional
    public void signUp(SignUpPlayerReq request) {
        validateDuplicated(request.getLogin_id());

        User user = userSignUpReqToUser(request, Role.PENDING);
        user.setLogin_pw(passwordEncoder.encode(user.getLogin_pw()));

        User savedUser = userRepository.save(user);

        Player player = Player.builder()
                .user(savedUser)
                .position(request.getPosition())
                .build();

        playerRepository.save(player);

    }

    /**
     * 감독 회원가입
     */
    @Transactional
    public void signUp(SignUpDirectorReq request) {
        validateDuplicated(request.getLogin_id());

        User user = userSignUpReqToUser(request, Role.PENDING);
        user.setLogin_pw(passwordEncoder.encode(user.getLogin_pw()));

        User savedUser = userRepository.save(user);

        Director director = Director.builder()
                .user(savedUser)
                .build();

        directorRepository.save(director);

    }

    /**
     * 사용자 아이디 중복 검사
     */
    public void validateDuplicated(String loginId) {
        userRepository.findUserByLoginId(loginId).ifPresent(
                (user) -> {
                    throw new RuntimeException("이미 존재하는 아이디 입니다.");
                }
        );
    }

    public User userSignUpReqToUser(SignUpUserReq request, Role role) {
        Team team = teamRepository.findTeamByCode(request.getTeamCode()).get();
        return User.builder()
                .login_id(request.getLogin_id())
                .login_pw(request.getLogin_pw())
                .name(request.getName())
                .imgUrl("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png")
                .team(team)
                .age(request.getAge())
                .role(role)
                .build();
    }

}
