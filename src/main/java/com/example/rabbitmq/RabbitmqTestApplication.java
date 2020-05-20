package com.example.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync  // 开启异步
public class RabbitmqTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqTestApplication.class, args);
	}

}
