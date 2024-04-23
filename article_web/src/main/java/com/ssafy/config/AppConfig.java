package com.ssafy.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
	@Bean
	public JndiObjectFactoryBean dataSource() {
		JndiObjectFactoryBean dataSource = new JndiObjectFactoryBean();
		dataSource.setJndiName("java:comp/env/jdbc/ssafy");
		return dataSource;
	}
}
