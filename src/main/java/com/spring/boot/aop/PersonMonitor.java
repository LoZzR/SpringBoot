package com.spring.boot.aop;

import com.spring.boot.services.PersonAudit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class PersonMonitor {

    @Autowired
    private PersonAudit audit;

    private static final Logger logger = LoggerFactory.getLogger(PersonMonitor.class);

    @Before("execution(public * com.spring.boot.controllers..*.*(..))")
    public void beforeFindById(JoinPoint joinPoint) {
        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        audit.recordAction(" ->> " + httpRequest.getRequestURI());
        String methodName = joinPoint.getSignature().getName();
        logger.info(" ---> Method " + methodName + " is about to be called");
    }
}
