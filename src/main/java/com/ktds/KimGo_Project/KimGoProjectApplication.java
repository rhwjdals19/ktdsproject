package com.ktds.KimGo_Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KimGoProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(KimGoProjectApplication.class, args);
	}

}
