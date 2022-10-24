package com.example.mentalCare.user.unit.service;

import com.example.mentalCare.user.domain.Player;
import com.example.mentalCare.user.domain.Team;
import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.type.Position;
import com.example.mentalCare.user.domain.type.Role;
import com.example.mentalCare.user.dto.GetUserInfoRes;
import com.example.mentalCare.user.dto.PlayerSignUpReq;
import com.example.mentalCare.user.repository.DirectorRepository;
import com.example.mentalCare.user.repository.PlayerRepository;
import com.example.mentalCare.user.repository.TeamRepository;
import com.example.mentalCare.user.repository.UserRepository;
import com.example.mentalCare.user.service.UserAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAuthServiceUnitTest {

    @InjectMocks
    private UserAuthService userAuthService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    public void 선수_회원가입_테스트() {
        // given
        Team team = new Team();
        team.setId(1L);
        team.setName("test_team");
        team.setCode("test_team_code");

        User user = new User();
        user.setLogin_id("test_login_id");
        user.setLogin_pw(passwordEncoder.encode("test_login_pw"));
        user.setName("test_name");
        user.setAge(10);
        user.setRole(Role.PENDING);
        user.setTeam(team);

        Player player = new Player();
        player.setUser(user);
        player.setPosition(Position.FW);

        // stub
        when(userRepository.findUserByLoginId("test_login_id")).thenReturn(Optional.empty());
        when(teamRepository.findTeamByCode("test_team_code")).thenReturn(Optional.of(team));
        when(userRepository.save(user)).thenReturn(user);

        // when
        PlayerSignUpReq request = new PlayerSignUpReq();
        request.setLogin_id("test_login_id");
        request.setLogin_pw("test_login_pw");
        request.setName("test_name");
        request.setAge(10);
        request.setPosition(Position.FW);
        request.setTeamCode("test_team_code");

        GetUserInfoRes userSignUpRes = userAuthService.signUp(request);

        // then
        assertEquals("test_login_id", userSignUpRes.getLogin_id());
    }
}
