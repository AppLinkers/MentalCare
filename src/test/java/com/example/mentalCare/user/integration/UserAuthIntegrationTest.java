package com.example.mentalCare.user.integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class UserAuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach // 모든 테스트 이전에 실행 됨
    public void init() {
        // 테이블 autoincrement 초기화
        // H2
        entityManager.createNativeQuery("ALTER TABLE _user ALTER COLUMN id RESTART WITH 1").executeUpdate();
        // MySQL - entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT =1").executeUpdate();
    }


}
