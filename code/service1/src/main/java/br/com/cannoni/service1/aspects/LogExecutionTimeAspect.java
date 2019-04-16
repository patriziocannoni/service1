package br.com.cannoni.service1.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author patrizio
 * @since 15/04/2019
 * 
 */
@Aspect
@Component
public class LogExecutionTimeAspect {

    private static final Logger LOGGER = LogManager.getLogger("RollingExecutionTimeLogger");
    
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        LOGGER.info(joinPoint.getSignature() + " executed in " + executionTime + " ms");
        return proceed;
    }

}
