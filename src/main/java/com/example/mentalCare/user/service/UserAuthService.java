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
    public ReadUserInfoRes signUp(PlayerSignUpReq request) {
        validateDuplicated(request.getLogin_id());

        User user = userSignUpReqToUser(request, Role.PLAYER);

        User savedUser = userRepository.save(user);

        Player player = Player.builder()
                .user(savedUser)
                .position(request.getPosition())
                .build();

        playerRepository.save(player);


        return ReadUserInfoRes.builder()
                .id(savedUser.getId())
                .login_id(savedUser.getLogin_id())
                .build();

    }

    @Transactional
    public ReadUserInfoRes signUp(DirectorSignUpReq request) {
        validateDuplicated(request.getLogin_id());

        User user = userSignUpReqToUser(request, Role.DIRECTOR);

        User savedUser = userRepository.save(user);

        Director director = Director.builder()
                .user(savedUser)
                .build();

        directorRepository.save(director);


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

    public String test() {
        return "userAuthService test";
    }


}
