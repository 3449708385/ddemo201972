package com.mgp.ddemo;

import com.mgp.ddemo.commons.util.RabbitSender;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.mgp"})
@MapperScan("com.mgp.ddemo.*.dao")
public class DdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdemoApplication.class, args);
		//new RedisUtilTest().testSet();

	}

}
