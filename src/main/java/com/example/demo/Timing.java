package com.example.demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class Timing {

    @Pointcut("execution(* *(..))")
    public void allMethods() {}

    @Around( "allMethods()" )
    public Object profile( final ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();
        try {
             Object time = joinPoint.proceed();
            return time;
        } finally {
            final long stop = System.currentTimeMillis();
            System.out.println("Execution time of "+ joinPoint.getSignature().getName() + " : "+ (stop-start)+" milliseconds");
        }
    }
}
