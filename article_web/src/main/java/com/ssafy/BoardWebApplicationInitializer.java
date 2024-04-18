package com.ssafy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.ssafy.config.WebConfig;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

public class BoardWebApplicationInitializer implements WebApplicationInitializer{
	private static final Logger log = LoggerFactory.getLogger(BoardWebApplicationInitializer.class);
	
	// 웹 애플리케이션 초기화 메서드
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		log.info("BoardWebApplicationInitializer.onStartup() call!!");
		
		// Annotation 기반의 Spring 컨텍스트 생성
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfig.class);
		// 생성된 Spring 컨텍스트를 서블릿 컨텍스트에 추가
		servletContext.addListener(new ContextLoaderListener(context));
		
		// DispatcherServlet을 서블릿 컨텍스트에 동적으로 등록
		DispatcherServlet servlet = new DispatcherServlet(context);
		ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
		
		// 애플리케이션 시작 시 로드되도록 설정
		registration.setLoadOnStartup(1);
		// 모든 요청에 대해 '/' 경로로 매핑
		registration.addMapping("/");
	}
}
