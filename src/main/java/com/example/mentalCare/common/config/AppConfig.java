package com.example.mentalCare.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaAuditing
public class AppConfig {

    /**
     *
     * PasswordEncoder - Spring5 부터 인코더 생성 방법이 변경되었다.
     * 다양한 암호화 알고리즘을 변경가능하도록 한것으로 보인다.
     *   처음에는 SecurityConfig 에 빈으로 생성하였으나 SecurityConfig 에서 FormAuthenticationProvider 를 의존하고
     *   FormAuthenticationProvider 는 passwordEncoder(SecurityConfig)에 의존하여 순환참조가 일어나
     *   따로 빈을 만드는 AppConfig를 생성하였다.
     *
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
