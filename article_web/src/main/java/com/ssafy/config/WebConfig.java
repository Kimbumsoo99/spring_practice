package com.ssafy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.interceptor.ConfirmInterceptor;

@Configuration	// 클래스를 빈으로 등록하고 설정 클래스로 사용
@EnableWebMvc	// 기본적인 Spring MVC 구성을 자동으로 활성화
@ComponentScan(basePackages = { "com.ssafy" }) // 지정된 패키지에서 컴포넌트를 검색하고 빈으로 등록
public class WebConfig implements WebMvcConfigurer{
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/styles/**").addResourceLocations("/WEB-INF/styles/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ConfirmInterceptor()).addPathPatterns("/").excludePathPatterns("/css/**", "/js/**");
	}
}
