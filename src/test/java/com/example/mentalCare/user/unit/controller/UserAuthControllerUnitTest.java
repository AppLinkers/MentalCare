package com.example.mentalCare.user.unit.controller;

import com.example.mentalCare.advice.FormAuthenticationFailureHandler;
import com.example.mentalCare.advice.FormAuthenticationProvider;
import com.example.mentalCare.advice.FormAuthenticationSuccessHandler;
import com.example.mentalCare.user.controller.UserAuthController;
import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.UserDetail;
import com.example.mentalCare.user.domain.type.Role;
import com.example.mentalCare.user.dto.UserLoginReq;
import com.example.mentalCare.user.service.UserAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserAuthController.class)
public class UserAuthControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                .andExpect(view().name("login"));

    }

    /**
    @Test
    public void 로그인_성공_테스트() throws Exception {
        // given
        User user = new User(1L, "test_login_id", passwordEncoder.encode("test_login_pw"), "test_name", 10, Role.PLAYER);

        // stub
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(Role.PLAYER.toString()));
        when(formAuthenticationProvider.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken("test_login_id", "test_login_pw", roles));

        UserLoginReq request = new UserLoginReq();
        request.setLogin_id("test_login_id");
        request.setLogin_pw("test_login_pw");
        String content = new ObjectMapper().writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post("/login_proc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        System.out.println(resultActions);
    }*/
}
