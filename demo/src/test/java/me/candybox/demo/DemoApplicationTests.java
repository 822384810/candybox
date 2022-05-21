package me.candybox.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.candybox.demo.mapper.DemoUserMapper;
import me.candybox.demo.model.DemoUser;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}



	/**
	 * MyBatis-Plus测试
	 * 
	 * 
	CREATE TABLE demo_user
	(
		id BIGINT(20) NOT NULL auto_increment COMMENT '主键ID',
		name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
		age INT(11) NULL DEFAULT NULL COMMENT '年龄',
		email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
		PRIMARY KEY (id)
	);
	//*/

	@Autowired
	private DemoUserMapper demoUserMapper ;


	@Test
	public void mybatisPlusTest(){
		DemoUser demoUser = new DemoUser();
		demoUser.setName("测试姓名3");
		demoUser.setAge(20);
		demoUser.setEmail("email@email.com");
		demoUserMapper.insert(demoUser);


	}

}
