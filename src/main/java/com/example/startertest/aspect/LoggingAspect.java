package com.example.startertest.aspect;

import com.example.startertest.log.LoggingAspectConfiguration;
import com.example.startertest.log.LoggingProperties;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import org.slf4j.event.Level;

import java.util.Arrays;


@Aspect
public class LoggingAspect {

    private final LoggingAspectConfiguration loggingAspectConfiguration;

    public LoggingAspect(LoggingProperties loggingProperties) {
        this.loggingAspectConfiguration = new LoggingAspectConfiguration(LoggingAspect.class, loggingProperties.getLevel());
    }

    @Before("@annotation(LogBefore)")
    public void logBeforeFunction(JoinPoint joinPoint) {
        loggingAspectConfiguration.logOut(String.format("Calling the method with the name = %s in the class %s", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringType().getName()));
    }

    @AfterThrowing(pointcut = "@annotation(LogException)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        loggingAspectConfiguration.logOut(String.format("Exception named %s occurred in a method named %s in the class %s", exception.getClass().getName(), joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringType().getName()));
    }

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) {
        loggingAspectConfiguration.logOut(String.format("Calling the execution time method with the name = %s in the class %s", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringType().getName()));
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length != 0) {
            loggingAspectConfiguration.logOut(String.format("Method named %s has args: %s", joinPoint.getSignature().getName(), Arrays.toString(args)));
        }

        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        }
        catch (Throwable e) {
            loggingAspectConfiguration.logOut("Error while getting result joinPoint.proceed", Level.ERROR);
            throw new RuntimeException(e);
        }
        long finishTime = System.currentTimeMillis();
        loggingAspectConfiguration.logOut(String.format("Aspect method %s execution time: %d ms", joinPoint.getSignature().getName(), finishTime - startTime));
        
        return result;
    }

    @AfterReturning(
            pointcut = "@annotation(LogHandling)",
            returning = "result"
    )
    public void handlingTaskResult(JoinPoint joinPoint, Object result) {
        loggingAspectConfiguration.logOut(String.format("Result Task of a method named %s: %s", joinPoint.getSignature().getName(), result.toString()));
    }

    @AfterReturning(
            pointcut = "@annotation(LogHandlingDto)",
            returning = "result"
    )
    public void handlingTaskDtoResult(JoinPoint joinPoint, Object result) {
        loggingAspectConfiguration.logOut(String.format("Result Task DTO of a method named %s: %s", joinPoint.getSignature().getName(), result.toString()));
    }

}
