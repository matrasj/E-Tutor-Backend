package com.example.etutorbackend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut(value = "execution(* com.example.etutorbackend.controller.*.*(..))")
    private void forControllerPackage() {}

    @Pointcut(value = "execution(* com.example.etutorbackend.repository.*.*(..))")
    private void forRepositoryPackage() {}

    @Pointcut(value = "execution(* com.example.etutorbackend.service.*.*(..))")
    private void forServicePackage() {}

    @Pointcut(value = "forControllerPackage() || forRepositoryPackage() || forServicePackage()")
    private void forAppFlow() {}

    @Before(value = "forAppFlow()")
    public void loggingBeforeEachMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toString();
        Object[] args = joinPoint.getArgs();

        log.info("Before calling method: " + methodName);
        for (Object arg : args) {
            log.info("Method argument: " + arg.toString());
        }
    }

    @AfterReturning(pointcut = "forAppFlow()", returning = "result")
    public void loggingAfterEachMethodReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toString();

        log.info("After calling method: " + methodName);
        log.info("Result from calling method: " + methodName + " is: " + result.toString());
    }

}
