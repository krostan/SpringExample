package com.luv2code.springboot.thymeleafdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {

    // set logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.controller.*.*(..))")
    public void forContorllerPackage(){}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.*(..))")
    public void forServicePackage(){}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.dao.*.*(..))")
    public void forDaoPackage(){}

    @Pointcut("forContorllerPackage() || forServicePackage() || forDaoPackage()")
    public void forAppFlow(){}

    // add @Before advice
    @Before("forAppFlow()")
    public void before(JoinPoint theJoinPoint){

        // display method we are calling
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("=====>>> in @Before : calling method : " + theMethod);

        // display the arguments to the method

        // get the arguments
        Object[] args = theJoinPoint.getArgs();

        // loop thru and display args
        for(Object tempArg : args){
            myLogger.info("=====>>> argument : " +tempArg);
        }

    }

    // add @AfterReturning advice
    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult){

        // display method we are returning from
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("=====>>> in @AfterReturning : from method : " + theMethod);

        // display data returned
        myLogger.info("====>>> result : " + theResult);

    }



}
