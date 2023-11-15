package com.youngzz1k.weblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.youngzz1k.weblog.*")
@EnableScheduling
public class WeblogWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeblogWebApplication.class, args);
	}

}
