package me.candybox.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"me.candybox.core.mapper","me.candybox.user.mapper"})
@SpringBootApplication(scanBasePackages ={"me.candybox","me.candybox.core","me.candybox.user"} )
public class CandyboxUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandyboxUserApplication.class, args);
	}

}
