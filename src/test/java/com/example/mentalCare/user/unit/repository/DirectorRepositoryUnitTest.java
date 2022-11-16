package com.example.mentalCare.user.unit.repository;

import com.example.mentalCare.user.domain.Director;
import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.type.Role;
import com.example.mentalCare.user.repository.DirectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
public class DirectorRepositoryUnitTest {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        // 테이블 autoincrement 초기화
        // H2
        entityManager.createNativeQuery("ALTER TABLE director ALTER COLUMN id RESTART WITH 2").executeUpdate();
        // MySQL - entityManager.createNativeQuery("ALTER TABLE director AUTO_INCREMENT =1").executeUpdate();
    }

    @Test
    public void save_테스트() {
        // given
        User user = new User();
        user.setLogin_id("test_login_id");
        user.setLogin_pw("test_login_pw");
        user.setName("test_name");
        user.setAge(10);
        user.setRole(Role.DIRECTOR);

        Director director = new Director();
        director.setUser(user);

        // when
        Director directorEntity = directorRepository.save(director);

        // then
        assertEquals("test_login_id", directorEntity.getUser().getLogin_id());
        assertEquals(Role.DIRECTOR, directorEntity.getUser().getRole());
    }
}
