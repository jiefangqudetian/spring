package com.kaishengit.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class MyAdvice {

    public void beforeAdvice(JoinPoint joinPoint){

        System.out.println("前置通知");

    }
    public void afterAdvice(JoinPoint joinPoint,Object result){
        System.out.println("后置通知");
        System.out.println("结果是"+result);
        Object[] args = joinPoint.getArgs();
        for (int i = 0;i<args.length;i++){
            System.out.println(args[i]+"123");
        }
    }
    public void exceptionAdvice(Exception e){
        System.out.println("异常通知");
        e.printStackTrace();
    }
    public void finallyAdvice(JoinPoint joinPoint){
        System.out.println("最终通知");
        Object[] args = joinPoint.getArgs();
        for (int i = 0;i<args.length;i++){
            System.out.println(args[i]+"123");
        }
    }
    public Object aroundAdvice(ProceedingJoinPoint joinPoint){
        Object result=null;
        try {
            System.out.println("前置通知");
            result=joinPoint.proceed();
            System.out.println("后置通知");
        } catch (Throwable throwable) {
            System.out.println("异常通知");
        }
        System.out.println("最终通知");
        return result;
    }

}
