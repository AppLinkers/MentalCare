package com.example.mentalCare.user.unit.repository;

import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.type.Role;
import com.example.mentalCare.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
public class UserRepositoryUnitTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        // 테이블 autoincrement 초기화
        // H2
        entityManager.createNativeQuery("ALTER TABLE _user ALTER COLUMN id RESTART WITH 2").executeUpdate();
        // MySQL - entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT =1").executeUpdate();
    }

    @Test
    public void save_테스트() {
        // given
        User user = new User();
        user.setLogin_id("test_login_id");
        user.setLogin_pw("test_login_pw");
        user.setName("test_name");
        user.setAge(10);
        user.setRole(Role.PLAYER);

        // when
        User userEntity = userRepository.save(user);

        // then
        assertEquals("test_login_id", userEntity.getLogin_id());
    }

    @Test
    public void 로그인아이디로조회_테스트() {
        // given
        User user = new User();
        user.setLogin_id("test_login_id");
        user.setLogin_pw("test_login_pw");
        user.setName("test_name");
        user.setAge(10);
        user.setRole(Role.PLAYER);

        userRepository.save(user);

        String loginId = "test_login_id";

        // when
        Optional<User> userEntity = userRepository.findUserByLoginId(loginId);

        // then
        assertTrue(userEntity.isPresent());
        assertEquals(2L, userEntity.get().getId());
        assertEquals("test_login_id", userEntity.get().getLogin_id());
    }
}
