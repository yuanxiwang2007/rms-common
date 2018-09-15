package com.rms.common.interceptor;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    public LogInterceptor() {
    }

    @Pointcut("@annotation(com.rms.common.interceptor.Log)")
    private void logAspect() {
    }

    @Before("logAspect()")
    public void log(JoinPoint joinPoint) {
        if (logger.isInfoEnabled()) {
            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
            Method method = signature.getMethod();
            Annotation[] declareAnnotations = method.getDeclaredAnnotations();
            Annotation[] var8 = declareAnnotations;
            int var7 = declareAnnotations.length;

            for(int var6 = 0; var6 < var7; ++var6) {
                Annotation annotation = var8[var6];
                if (annotation instanceof Log) {
                    Log description = (Log)annotation;
                    Object[] args = joinPoint.getArgs();
                    logger.debug(description.value()[0] + ",方法名:{},参数:{}", method.getName(), JSON.toJSONString(args));
                }
            }
        }

    }
}

