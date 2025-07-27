package com.rdele.drone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DroneBasedDeliveryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneBasedDeliveryApiApplication.class, args);
	}

}
