package com.youngzz1k.weblog;

//import com.youngzz1k.weblog.common.domain.dos.UserDo;
import com.youngzz1k.weblog.common.domain.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
@Slf4j
class WeblogWebApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private UserMapper userMapper;

	@Test
	void testLog(){
		log.info("info 级别 。。。");
		log.warn("warn 级别 。。。");
		log.error("error 级别 。。。");

		String author = "YoungZz1k";
		log.info("占位符日志 {}",author);
	}

//	@Test
//	void insertTest(){
//		UserDo user = new UserDo();
//		user.setUsername("YoungZz1k");
//		user.setPassword("907175262");
//		user.setUpdateTime(new Date());
//		user.setCreateTime(new Date());
//		user.setIsDeleted(false);
//
//		userMapper.insert(user);
//	}

}
