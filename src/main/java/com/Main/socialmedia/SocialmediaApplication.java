package com.Main.socialmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages="com.controller")
@EnableJpaRepositories({"com.dao"})
@EntityScan(basePackages= {"com.model"})
public class SocialmediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialmediaApplication.class, args);
	}

}
