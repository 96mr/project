package com.spring.ex02.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAop {
	private static final Logger log = LoggerFactory.getLogger(LogAop.class);
	
	//around가 적용될 포인트컷
	@Around("within(com.spring.ex02.service.*)")
	public Object loggerAop(ProceedingJoinPoint joinpoint) throws Throwable{
		String signatureStr = joinpoint.getSignature().toShortString(); //기능이 적용되는 메서드 명
		long startTime =  System.currentTimeMillis();
		try {
			Object obj = joinpoint.proceed();//핵심 기능 실행	
			return obj;
		}finally {
			long endTime = System.currentTimeMillis();
			log.info( signatureStr +" 수행 시간 : " +(endTime-startTime) +"ms");
		}
	}
	
}
