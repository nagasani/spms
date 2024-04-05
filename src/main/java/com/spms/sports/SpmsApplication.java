package com.spms.sports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = {"com.spms.sports.entity"})
@EnableScheduling
public class SpmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpmsApplication.class, args);
	}

}
