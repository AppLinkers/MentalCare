package com.example.mentalCare.user.service;

import com.example.mentalCare.user.domain.Director;
import com.example.mentalCare.user.domain.Player;
import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.UserDetail;
import com.example.mentalCare.user.domain.type.Role;
import com.example.mentalCare.user.dto.DirectorSignUpReq;
import com.example.mentalCare.user.dto.PlayerSignUpReq;
import com.example.mentalCare.user.dto.ReadUserInfoRes;
import com.example.mentalCare.user.dto.UserSignUpReq;
import com.example.mentalCare.user.repository.DirectorRepository;
import com.example.mentalCare.user.repository.PlayerRepository;
import com.example.mentalCare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final DirectorRepository directorRepository;
    private final PlayerRepository playerRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin_id(username).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return UserDetail.builder()
                .username(user.getLogin_id())
                .password(user.getLogin_pw())
                .authorityList(roles)
                .build();
    }

    @Transactional
    public ReadUserInfoRes signUp(UserSignUpReq request) {
        validateDuplicated(request.getLogin_id());

        User savedUser = new User();

        if (request.getClass().equals(PlayerSignUpReq.class)) {
            User user = userSignUpReqToUser(request, Role.PLAYER);

            savedUser = userRepository.save(user);

            PlayerSignUpReq playerSignUpReq = (PlayerSignUpReq) request;

            Player player = Player.builder()
                    .user(savedUser)
                    .position(playerSignUpReq.getPosition())
                    .build();

            playerRepository.save(player);
        } else if (request.getClass().equals(DirectorSignUpReq.class)) {
            User user = userSignUpReqToUser(request, Role.DIRECTOR);

            savedUser = userRepository.save(user);

            DirectorSignUpReq directorSignUpReq = (DirectorSignUpReq) request;

            Director director = Director.builder()
                    .user(savedUser)
                    .build();

            directorRepository.save(director);

        }

        return ReadUserInfoRes.builder()
                .id(savedUser.getId())
                .login_id(savedUser.getLogin_id())
                .build();

    }

    public void validateDuplicated(String login_id) {
        userRepository.findUserByLogin_id(login_id).ifPresent(
                (user) -> {
                    throw new RuntimeException("이미 존재하는 아이디 입니다.");
                }
        );
    }

    public User userSignUpReqToUser(UserSignUpReq request, Role role) {
        return User.builder()
                .login_id(request.getLogin_id())
                .login_pw(passwordEncoder.encode(request.getLogin_pw()))
                .name(request.getName())
                .age(request.getAge())
                .role(role)
                .build();
    }


}
