package com.afk.testtechnique.supervision;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogSupervision {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogSupervision.class);

    @Around("@annotation(supervision)")
    public Object apiSupervision(ProceedingJoinPoint joinPoint, Supervision supervision) throws Throwable {
        long start = System.currentTimeMillis();
        if(LOGGER.isDebugEnabled()) {
            String args = Arrays.toString(joinPoint.getArgs());
            LOGGER.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), args);
        }
        try {
            Object result = joinPoint.proceed();
            LOGGER.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);
            return result;
        } finally {
            long end = System.currentTimeMillis();
            long duree = end - start;
            LOGGER.debug("{}.{}() execution time = {}ms", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), duree);
        }
    }


}