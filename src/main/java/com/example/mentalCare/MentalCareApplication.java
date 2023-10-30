package com.example.mentalCare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MentalCareApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentalCareApplication.class, args);
	}

}
