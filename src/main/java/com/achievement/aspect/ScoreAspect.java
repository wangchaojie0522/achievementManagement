package com.achievement.aspect;


import com.achievement.utils.GloabalUtils;
import com.achievement.vo.ResultEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * AOP处理
 *
 * @author weiQiang
 */
@Aspect
@Component
public class ScoreAspect {

  private final static Logger LOGGER = LoggerFactory.getLogger(ScoreAspect.class);

  /**
   * 开始时间,用于记录请求耗时
   */
  private static long beginTime = System.currentTimeMillis();

  /**
   * 请求之后拦截
   */
  @After("doController()")
  public void doAfter() {

  }

  /**
   * 返回时拦截
   *
   * @param object
   */
  @AfterReturning(returning = "object", pointcut = "doController()")
  public void doAfterReturning(Object object) {
    if (null != object) {
      if (object instanceof ResultEntity) {
        ((ResultEntity) object).setTotalTime((System.currentTimeMillis() - beginTime));
        if (null != ((ResultEntity) object).getData() && (((ResultEntity) object).getData() instanceof ArrayList) && !((ResultEntity) object).isPageable()) {
          ((ResultEntity) object).setTotal((long) ((ResultEntity) object).getData().size());
        }
      }
      LOGGER.debug("response:{}", object);
    } else {
      LOGGER.debug("response:{}", "无返回值");
    }
  }

  /**
   * 请求之前拦截
   *
   * @param joinPoint
   */
  @Before("doController()")
  public void doBefore(JoinPoint joinPoint) {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    beginTime = System.currentTimeMillis();
    LOGGER.info("url:{},方法:{},请求ip:{},类和方法:{}(),参数:{}", request.getRequestURL(), request.getMethod(), GloabalUtils.getIpAddress(request), joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(), joinPoint.getArgs());

  }

  /**
   * 切点
   */
  @Pointcut(value = "execution(* com.achievement.controller.*.*(..))")
  public void doController() {
  }
}
