package com.example.mentalCare.user.unit.controller;

import com.example.mentalCare.advice.FormAuthenticationFailureHandler;
import com.example.mentalCare.advice.FormAuthenticationProvider;
import com.example.mentalCare.advice.FormAuthenticationSuccessHandler;
import com.example.mentalCare.user.controller.UserAuthController;
import com.example.mentalCare.user.domain.type.Position;
import com.example.mentalCare.user.dto.DirectorSignUpReq;
import com.example.mentalCare.user.dto.PlayerSignUpReq;
import com.example.mentalCare.user.dto.ReadUserInfoRes;
import com.example.mentalCare.user.service.UserAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserAuthController.class)
public class UserAuthControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthService userAuthService;

    @MockBean
    private FormAuthenticationSuccessHandler formAuthenticationSuccessHandler;

    @MockBean
    private FormAuthenticationProvider formAuthenticationProvider;

    @MockBean
    private FormAuthenticationFailureHandler formAuthenticationFailureHandler;

    @Test
    public void 로그인_페이지_테스트() throws Exception {

        // when
        ResultActions resultActions = mockMvc.perform(get("/login"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"));

    }

    /**
     * @Test public void 로그인_성공_테스트() throws Exception {
     * // given
     * User user = new User(1L, "test_login_id", passwordEncoder.encode("test_login_pw"), "test_name", 10, Role.PLAYER);
     * <p>
     * // stub
     * List<GrantedAuthority> roles = new ArrayList<>();
     * roles.add(new SimpleGrantedAuthority(Role.PLAYER.toString()));
     * when(formAuthenticationProvider.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken("test_login_id", "test_login_pw", roles));
     * <p>
     * UserLoginReq request = new UserLoginReq();
     * request.setLogin_id("test_login_id");
     * request.setLogin_pw("test_login_pw");
     * String content = new ObjectMapper().writeValueAsString(request);
     * <p>
     * // when
     * ResultActions resultActions = mockMvc.perform(post("/login_proc")
     * .contentType(MediaType.APPLICATION_JSON)
     * .content(content));
     * <p>
     * System.out.println(resultActions);
     * }
     */

    @Test
    public void 선수_회원가입_테스트() throws Exception {
        // given
        PlayerSignUpReq request = new PlayerSignUpReq();
        request.setLogin_id("test_login_id");
        request.setLogin_pw("test_login_pw");
        request.setName("test_name");
        request.setAge(10);
        request.setPosition(Position.FW);

        // stub
        when(userAuthService.signUp(request)).thenReturn(ReadUserInfoRes.builder()
                .id(1L)
                .login_id("test_login_id")
                .build());

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
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    public void 감독_회원가입_테스트() throws Exception {
        // given
        DirectorSignUpReq request = new DirectorSignUpReq();
        request.setLogin_id("test_login_id");
        request.setLogin_pw("test_login_pw");
        request.setName("test_name");
        request.setAge(10);

        // stub
        when(userAuthService.signUp(request)).thenReturn(ReadUserInfoRes.builder()
                .id(1L)
                .login_id("test_login_id")
                .build());

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
                .andExpect(view().name("redirect:/login"));
    }
}
