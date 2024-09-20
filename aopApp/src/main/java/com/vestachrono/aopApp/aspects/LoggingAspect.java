package com.vestachrono.aopApp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect
@Component
@Slf4j
public class LoggingAspect {
//    @Before("execution(* orderPackage(..))")
//    @Before("execution(* com.vestachrono.aopApp.services.impl.ShipmentServiceImpl.*.orderPackage(..))")
    @Before("execution(* com.vestachrono.aopApp.services.impl.*.*(..))")
    public void beforeOrderPackage(JoinPoint joinPoint) {
        log.info("Before called from LoggingAspect kind, {}", joinPoint.getKind());
        log.info("Before called from LoggingAspect signature, {}", joinPoint.getSignature());
    }

    @Before("within(com.vestachrono.aopApp..*)")
    public void beforeServiceImpl() {
        log.info("Service Impl calls");
    }

    @Before("myLoggingAndAopMethodPointCut()")
    public void beforeTransactionalAnnotationCalls() {
        log.info("Before MyLogging annotations calls");
    }

    @After("myLoggingAndAopMethodPointCut()")
    public void afterMyLoggingAndAopMethodPointCut() {
        log.info("After MyLogging annotations calls");
    }

    @Pointcut("@annotation(com.vestachrono.aopApp.aspects.MyLogging) && within(com.vestachrono.aopApp..*)")
    public void myLoggingAndAopMethodPointCut() {

    }

}
