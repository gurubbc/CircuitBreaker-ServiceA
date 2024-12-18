package com.lowes;

import java.net.ConnectException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;


@RestController
@RequestMapping("/a")
public class ServiceAController {
	
	@Autowired
	RestTemplate restTemplate;
	
	private static final String BASE_URL="http://localhost:8081/";
	int times=1;
	@GetMapping
	@CircuitBreaker(name = "serviceA", fallbackMethod = "serviceAFallBack")
	@Retry(name = "serviceA", fallbackMethod = "serviceAFallBack")
	public String serviceA() {
	    String url = BASE_URL + "/b";
	    try {
	        // Log the retry count and attempt to call Service B
	        System.out.println("Attempting to call Service B... Retry count: " + times);
	        String response = restTemplate.getForObject(url, String.class);
	        System.out.println("Service B response: " + response);
	        return response;
	    } catch (ResourceAccessException e) {
	        // Log the error and retry count
	        System.out.println("Failed to call Service B. Retry count: " + times);
	        throw e; // This triggers the retry mechanism
		} catch (Exception e) {
			// Log the error and retry count
			System.out.println("Failed to call Service B. Retry count: " + times);
			throw e; // This triggers the retry mechanism
		}
	    
	}

	public String serviceAFallBack(Exception e) {
	    System.out.println("Fallback method triggered: " + e.getMessage());
	    return "Service B is down. Fallback executed.";
	}


	
}
