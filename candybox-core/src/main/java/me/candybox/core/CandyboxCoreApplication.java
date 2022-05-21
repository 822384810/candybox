package me.candybox.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ="me.candybox" )
public class CandyboxCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandyboxCoreApplication.class, args);
	}

}
