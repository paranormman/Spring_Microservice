package com.vestachrono.aopApp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MyLoggingAspectV2 {

    @Before("allServiceMethodPointCut()")
    public void beforeServiceMethodCalls(JoinPoint joinPoint) {
        log.info("Before advice method call {}", joinPoint.getSignature());
    }

//    @After("allServiceMethodPointCut()")
    @AfterReturning(value = "allServiceMethodPointCut()", returning = "returnedObj")
    public void afterServiceMethodCalls(JoinPoint joinPoint, Object returnedObj) {
        log.info("After advice method call {}", joinPoint.getSignature());
        log.info("After advice method call returned object is: {}", returnedObj);
    }

    @AfterThrowing("allServiceMethodPointCut()")
    public void afterServiceMethodCallsThrows(JoinPoint joinPoint) {
        log.info("After throws advice calls {}", joinPoint.getSignature());
    }

    @Around("allServiceMethodPointCut()")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object returnedValue = proceedingJoinPoint.proceed();
        Long endTime = System.currentTimeMillis();

        Long diff = endTime - startTime;
        log.info("Time taken for {} is {}", proceedingJoinPoint.getSignature(), diff);
        return returnedValue;
    }

    @Pointcut("execution(* com.vestachrono.aopApp.services.impl.*.*(..))")
    public void allServiceMethodPointCut() {
    }
}
