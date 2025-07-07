package com.proyectoPWP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.proyectoPWP.domain")
public class FideGymApplication {

	public static void main(String[] args) {
		SpringApplication.run(FideGymApplication.class, args);
	}

}
