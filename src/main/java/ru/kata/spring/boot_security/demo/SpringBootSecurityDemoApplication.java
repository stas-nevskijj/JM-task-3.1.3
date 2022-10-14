package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}


}
