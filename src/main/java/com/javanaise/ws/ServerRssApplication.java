package com.javanaise.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerRssApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerRssApplication.class, args);
	}
}
