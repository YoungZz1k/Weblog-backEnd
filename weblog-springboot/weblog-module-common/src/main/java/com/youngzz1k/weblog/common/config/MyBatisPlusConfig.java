package com.youngzz1k.weblog.common.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.youngzz1k.weblog.common.domain.mapper")
public class MyBatisPlusConfig {
}
