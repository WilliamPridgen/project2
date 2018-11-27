package com.example.demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Sold {

    @Pointcut("execution(* *(..))")
    public void publicMethod() {}


    @After("@annotation(Sell)")
    public void after(JoinPoint joinPoint){

        System.out.println("This vehicle has no been sold");
    }
}
