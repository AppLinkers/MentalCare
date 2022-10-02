package com.example.mentalCare.user.integration;

import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.type.Position;
import com.example.mentalCare.user.domain.type.Role;
import com.example.mentalCare.user.dto.DirectorSignUpReq;
import com.example.mentalCare.user.dto.PlayerSignUpReq;
import com.example.mentalCare.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class UserAuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach // 모든 테스트 이전에 실행 됨
    public void init() {
        // 테이블 autoincrement 초기화
        // H2
        entityManager.createNativeQuery("ALTER TABLE _user ALTER COLUMN id RESTART WITH 1").executeUpdate();
        // MySQL - entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT =1").executeUpdate();

    }

    @Test
    public void 선수_회원가입_테스트() throws Exception {
        // given
        PlayerSignUpReq request = new PlayerSignUpReq();
        request.setLogin_id("test_login_id");
        request.setLogin_pw("test_login_pw");
        request.setName("test_name");
        request.setAge(10);
        request.setPosition(Position.FW);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/sign_up/player")
                        .param("login_id", "test_login_id")
                        .param("login_pw", "test_login_pw")
                        .param("name", "test_name")
                        .param("age", "10")
                        .param("position", "FW")
        );

        // then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void 감독_회원가입_테스트() throws Exception {
        // given
        DirectorSignUpReq request = new DirectorSignUpReq();
        request.setLogin_id("test_login_id");
        request.setLogin_pw("test_login_pw");
        request.setName("test_name");
        request.setAge(10);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/sign_up/director")
                        .param("login_id", "test_login_id")
                        .param("login_pw", "test_login_pw")
                        .param("name", "test_name")
                        .param("age", "10")
        );

        // then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "test_login_id")
    @Test
    public void 로그인_성공_테스트() throws Exception {
        // given
        // data 생성
        User user = User.builder()
                .login_id("test_login_id")
                .login_pw(passwordEncoder.encode("test_login_pw"))
                .name("test_name")
                .age(10)
                .role(Role.PLAYER)
                .build();

        userRepository.save(user);

        // when
        ResultActions resultActions = mockMvc.perform(
                formLogin("/login_proc")
                .user("test_login_id")
                .password("test_login_pw"));

        // then
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(Role.PLAYER.toString()));

        resultActions
                .andExpect(authenticated().withUsername("test_login_id").withAuthorities(authorityList))
                .andExpect(status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print());
    }
}
