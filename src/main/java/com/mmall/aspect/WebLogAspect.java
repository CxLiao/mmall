package com.mmall.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Web层切面日志
 *
 * @author liaocx
 * @date 2018/10/31
 */
@Aspect
@Component
public class WebLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    private Logger logger = Logger.getLogger(getClass());

    @Pointcut("execution(public * com.mmall.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
            System.out.println("\r\n");
            logger.info("地址 : " + request.getRequestURL().toString());
            logger.info("请求方式 : " + request.getMethod());
            logger.info("IP : " + request.getRemoteAddr());
            logger.info("执行的方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            Object[] args = joinPoint.getArgs().clone();
            logger.info("参数 : " + Arrays.toString(args));
        }
    }

    /**
     * 处理完请求，返回内容
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        logger.info("返回内容 : " + ret);
        logger.info("花费时间 : " + (System.currentTimeMillis() - startTime.get()) + "毫秒");
    }
}
