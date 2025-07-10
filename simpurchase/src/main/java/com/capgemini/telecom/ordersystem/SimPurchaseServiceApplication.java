package com.capgemini.telecom.ordersystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimPurchaseServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(SimPurchaseServiceApplication.class, args);
	}

}
