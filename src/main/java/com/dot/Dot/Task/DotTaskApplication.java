package com.dot.Dot.Task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DotTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(DotTaskApplication.class, args);
	}

}
