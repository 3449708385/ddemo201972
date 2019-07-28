package com.mgp.ddemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mgp"})
@MapperScan("com.mgp.ddemo.*.dao")
@EnableAsync
@EnableScheduling
public class DdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdemoApplication.class, args);
	}

}
