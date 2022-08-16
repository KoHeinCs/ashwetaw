package com.ashwetaw;

import com.ashwetaw.email.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootJwtApplicationTests {
	@Autowired
	EmailService emailService;

	@Test
	void contextLoads() {
		emailService.sendNewPasswordEmail("Hein Htet Aung","heinhtetaungcu@gmail.com","welcomesip");
	}

}
