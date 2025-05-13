package com.ignite.email_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailServiceApplication {

	public static void main(String[] args) {

		// Carrega o .env
		Dotenv dotenv = Dotenv.load();

		// Injeta como System properties para o Spring resolver
		System.setProperty("SENDGRID_API_KEY", dotenv.get("SENDGRID_API_KEY"));
		System.setProperty("SENDGRID_FROM_EMAIL", dotenv.get("SENDGRID_FROM_EMAIL"));

		SpringApplication.run(EmailServiceApplication.class, args);
	}

}
