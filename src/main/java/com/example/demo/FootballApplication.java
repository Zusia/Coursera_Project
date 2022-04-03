package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.sql.SQLException;

@EnableJpaRepositories
@SpringBootApplication
public class FootballApplication {

	public static void main(String[] args) throws IOException, SQLException {

		SpringApplication.run(FootballApplication.class, args);
		String jdbcURL = "jdbc:mysql://localhost:3306/sales";
		String username = "root";
		String password = "Sush1234";
			}

}
