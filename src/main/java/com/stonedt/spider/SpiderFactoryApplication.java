package com.stonedt.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpiderFactoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiderFactoryApplication.class, args);
	}

}
