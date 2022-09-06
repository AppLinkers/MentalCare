package com.example.mentalCare.user.unit.repository;

import com.example.mentalCare.user.domain.Player;
import com.example.mentalCare.user.domain.User;
import com.example.mentalCare.user.domain.type.Position;
import com.example.mentalCare.user.domain.type.Role;
import com.example.mentalCare.user.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
public class PlayerRepositoryUnitTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        // 테이블 autoincrement 초기화
        // H2
        entityManager.createNativeQuery("ALTER TABLE player ALTER COLUMN id RESTART WITH 1").executeUpdate();
        // MySQL - entityManager.createNativeQuery("ALTER TABLE player AUTO_INCREMENT =1").executeUpdate();
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

        Player player = new Player();
        player.setUser(user);
        player.setPosition(Position.FW);

        // when
        Player playerEntity = playerRepository.save(player);

        // then
        assertEquals("test_login_id", playerEntity.getUser().getLogin_id());
        assertEquals(Role.PLAYER, playerEntity.getUser().getRole());
        assertEquals(Position.FW, playerEntity.getPosition());
    }

}
