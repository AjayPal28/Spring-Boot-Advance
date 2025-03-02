package com.ajay.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

	@Before("execution(* orderPackage(..))")
	public void beforeOrderPackage(JoinPoint joinPoint) {
		log.info("Before orderPackage called from Logging aspect, {}", joinPoint.getKind());
		log.info("Before orderPackage called from Logging aspect, {}", joinPoint.getSignature());
		log.info("Before orderPackage called from Logging aspect, {}", joinPoint.getSourceLocation());
	}
	
	@After("execution(* orderPackage(..))")
	public void after(JoinPoint joinPoint) {
		log.info("Before orderPackage called from Logging aspect, {}", joinPoint.getKind());
		log.info("Before orderPackage called from Logging aspect, {}", joinPoint.getSignature());
		log.info("Before orderPackage called from Logging aspect, {}", joinPoint.getSourceLocation());
	}
	
	@Before("within(com.ajay.service.impl..*)")
	public void beforeServideImpl(JoinPoint joinPoint) {
		log.info("BbeforeServideImpl, {}", joinPoint.getKind());
		log.info("BbeforeServideImpl, {}", joinPoint.getSignature());
		log.info("beforeServideImpl, {}", joinPoint.getSourceLocation());
	}
	
	@Before("pointCut()")
	public void pointCut(JoinPoint joinPoint) {
		log.info("pointCut, {}", joinPoint.getKind());
		log.info("pointCut, {}", joinPoint.getSignature());
		log.info("pointCut, {}", joinPoint.getSourceLocation());
	}
	
	@Before("@annotation(org.springframework.transaction.annotation.Transactional)")
	public void beforeTransactionAnnotationCall(JoinPoint joinPoint) {
		log.info("beforeTransactionAnnotationCall, {}", joinPoint.getKind());
		log.info("beforeTransactionAnnotationCall, {}", joinPoint.getSignature());
		log.info("beforeTransactionAnnotationCall, {}", joinPoint.getSourceLocation());
	}
	
	@AfterReturning(value="pointCut()", returning="obj")
	public void afterReturning(JoinPoint joinPoint, Object obj) {
		log.info("after returnig"+obj);
	}
	
	@AfterThrowing(value="pointCut()")
	public void afterThrowing(JoinPoint joinPoint) {
		log.info("afterThrowing");
	}
	
	@Pointcut("within(com.ajay.service.impl..*)")
	public void pointCut() {
		
	}
	
	@Around(value = "pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object  ar[]=joinPoint.getArgs();
		long l=(long)ar[0];
		if(l<1)
			return "Cannot pass value less than 1";
		
		return joinPoint.proceed();
	}
	
}
