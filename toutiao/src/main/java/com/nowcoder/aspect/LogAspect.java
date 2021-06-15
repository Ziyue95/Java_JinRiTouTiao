package com.nowcoder.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by nowcoder on 2016/6/26.
 */

/** Note that this is an aspect */
@Aspect
@Component
/** Record all log infos */
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /** Before execute all controllers
     * (*Controller:(regular expression) name end with controller)
     * belongs to class com.nowcoder.controller */
    @Before("execution(* com.nowcoder.controller.*Controller.*(..))")
    /** Inject Joinpoint: the interface object when aspect join with methods */
    public void beforeMethod(JoinPoint joinPoint) {
        /** Use StringBuilder to collect infos from all joinpoints */
        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            /** Collect all parameters(args) from joinpoints */
            sb.append("arg:" + arg.toString() + "|");
        }
        logger.info("before method: " + sb.toString());
    }
    /** After execute all methods belongs to class com.nowcoder.controller */
    @After("execution(* com.nowcoder.controller.IndexController.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        logger.info("after method: ");
    }
}
