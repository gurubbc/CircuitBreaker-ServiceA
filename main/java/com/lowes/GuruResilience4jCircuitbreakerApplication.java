package com.lowes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GuruResilience4jCircuitbreakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuruResilience4jCircuitbreakerApplication.class, args);
		System.out.println("Service A started");
	}
	
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

}
