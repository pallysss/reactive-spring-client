package com.ply.reactive.reactivemongoclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ply.reactive.reactivemongoclient.model.Employee;
import com.ply.reactive.reactivemongoclient.model.EmployeeEvent;

@SpringBootApplication
public class ReactiveMongoClientApplication {
	
	@Bean
	WebClient webClient() {
		return WebClient.create("http://localhost:8082/rest/employee");
	}

	@Bean
	CommandLineRunner commandLineRunner(WebClient webClient) {
		return strings -> {
			webClient
				.get()
				.uri("/all")
				.retrieve()
				.bodyToFlux(Employee.class)
//				.filter(employee -> employee.getName().equals("Peter")
//				)
				.flatMap(employee -> {
					return webClient.get()
						.uri("/{id}/events", employee.getId())
						.retrieve()
						.bodyToFlux(EmployeeEvent.class);
				})
				.subscribe(employeeEvent -> {
					System.out.println(employeeEvent);
				});
			System.out.print("Command LINE EXECUTED");
		};		
	}
	
//	@Bean
//	CommandLineRunner commandLineRunner(WebClient webClient, ObjectMapper objectMapper) {
//		return strings -> {
//			 webClient.get()
//					.uri("/f4ca0ae2-bfe1-4f1e-a90a-b86843f06d63/events")
//					.retrieve()
//					.bodyToFlux(EmployeeEvent.class)
//					.doOnError(err -> System.out.println("ERROR IS >> "+err ))
//					
//					.subscribe(System.out::println);
//			 
////			 for (int i=0; i<= 1000; i++) {
////				 System.out.println("Blocking => "+i);
////				 Thread.sleep(1000);
////			 }
//		};		
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(ReactiveMongoClientApplication.class, args);
	}
}
