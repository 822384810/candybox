package me.candybox.regeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegEurekaApplication.class, args);
	}

}
