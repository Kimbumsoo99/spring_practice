package com.ssafy.member.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@MapperScan(basePackages = { "com.ssafy.member.**.mapper" })
public class WebMvcConfig {

}
