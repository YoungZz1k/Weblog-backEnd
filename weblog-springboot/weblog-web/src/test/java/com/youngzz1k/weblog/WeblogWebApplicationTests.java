package com.youngzz1k.weblog;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class WeblogWebApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testLog(){
		log.info("info 级别 。。。");
		log.warn("warn 级别 。。。");
		log.error("error 级别 。。。");

		String author = "YoungZz1k";
		log.info("占位符日志 {}",author);
	}

}
