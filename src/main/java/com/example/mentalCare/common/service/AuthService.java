package com.example.mentalCare.common.service;

import com.example.mentalCare.common.domain.User;
import com.example.mentalCare.common.domain.UserDetail;
import com.example.mentalCare.common.domain.type.Role;
import com.example.mentalCare.common.dto.*;
import com.example.mentalCare.common.repository.UserRepository;
import com.example.mentalCare.consultant.profile.domain.Consultant;
import com.example.mentalCare.consultant.profile.repository.ConsultantRepository;
import com.example.mentalCare.director.profile.domain.Director;
import com.example.mentalCare.director.profile.repository.DirectorRepository;
import com.example.mentalCare.player.profile.domain.Player;
import com.example.mentalCare.player.profile.repository.PlayerRepository;
import com.example.mentalCare.team.domain.Team;
import com.example.mentalCare.team.repository.TeamRepository;
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
    private final ConsultantRepository consultantRepository;
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
     * 선수 회원가입 서비스
     */
    @Transactional
    public void signUp(SignUpPlayerReq request) {
        validateDuplicated(request.getLogin_id());

        User user = userSignUpReqToUser(request, Role.PLAYER_PENDING);
        user.setLogin_pw(passwordEncoder.encode(user.getLogin_pw()));

        Player player = Player.builder()
                .user(user)
                .position(request.getPosition())
                .build();

        userRepository.save(user);
        playerRepository.save(player);

    }

    /**
     * 지도자 회원가입 서비스
     */
    @Transactional
    public void signUp(SignUpDirectorReq request) {
        validateDuplicated(request.getLogin_id());

        User user = userSignUpReqToUser(request, Role.DIRECTOR_PENDING);
        user.setLogin_pw(passwordEncoder.encode(user.getLogin_pw()));

        Director director = Director.builder()
                .user(user)
                .build();

        userRepository.save(user);
        directorRepository.save(director);

    }

    /**
     * 상담가 회원가입 서비스
     */
    @Transactional
    public void signUp(SignUpConsultantReq request) {
        validateDuplicated(request.getLogin_id());

        User user = userSignUpReqToUser(request, Role.CONSULTANT_PENDING);
        user.setLogin_pw(passwordEncoder.encode(user.getLogin_pw()));

        Consultant consultant = Consultant.builder()
                .user(user)
                .build();
        userRepository.save(user);
        consultantRepository.save(consultant);

    }

    /**
     * 사용자 아이디 중복 검사
     */
    @Transactional(readOnly = true)
    public void validateDuplicated(String loginId) {
        userRepository.findUserByLoginId(loginId).ifPresent(
                (user) -> {
                    throw new RuntimeException("이미 존재하는 아이디 입니다.");
                }
        );
    }

    /**
     * UserSignUpReq -> User
     */
    public User userSignUpReqToUser(SignUpUserReq request, Role role) {

        User user =  User.builder()
                .login_id(request.getLogin_id())
                .login_pw(request.getLogin_pw())
                .name(request.getName())
                .imgUrl("https://mindup.s3.ap-northeast-2.amazonaws.com/profile.png")
                .birthDate(request.getBirthDate())
                .privacyPolicy(request.getPrivacyPolicy())
                .role(role)
                .build();


        if (!request.getTeamCode().equals("")) {
            Team team = teamRepository.findTeamByCode(request.getTeamCode()).get();
            user.setTeam(team);
            team.addUser(user);
        } else {
            if (role.equals(Role.PLAYER_PENDING)) {
                user.setRole(Role.PLAYER);
            }
        }

        return user;
    }



    @Transactional
    public void changePassword(String loginId, ChangePwReq changePwReq){
        User user = userRepository.findUserByLoginId(loginId).get();
        user.setLogin_pw(passwordEncoder.encode(changePwReq.getNewPw()));
    }

}
