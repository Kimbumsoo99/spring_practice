package com.ssafy.ws.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ArticleAspect {
	public static final Logger logger = Logger.getLogger("serviceLogger");

	// 코드작성
	@Pointcut("execution(* com.ssafy.ws.model.service.*ServiceImpl.*Article(..))")
	public void articleMethods() {
	}

	@Before("articleMethods()")
	public void logBeforeService(JoinPoint joinPoint) {
		logger.info("========== 서비스 시작 ==========");
	}

	@AfterReturning("articleMethods()")
	public void logAfterService(JoinPoint joinPoint) {
		logger.info("========== 서비스 종료 ==========");
	}

}
