package com.spms.sports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.spms.sports.entity"})
public class SpmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpmsApplication.class, args);
	}

}
