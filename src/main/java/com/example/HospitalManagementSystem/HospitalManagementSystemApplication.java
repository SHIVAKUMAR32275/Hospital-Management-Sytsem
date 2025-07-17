package com.example.HospitalManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class HospitalManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementSystemApplication.class, args);
	}
}
