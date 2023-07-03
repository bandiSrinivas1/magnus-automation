/**
 * 
 */
package com.classplusapp.common;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author nares
 *
 */
@Aspect
public class ClassplusLoggerAspect {
	
	protected static final Logger log = LogManager.getLogger(ClassplusLoggerAspect.class);

	@Pointcut("execution(* com.phptravels..*.*(..))")
	private void selectAllPhpTravels(){}

	@Around("selectAllPhpTravels()")
	public Object aroundTestMethod(ProceedingJoinPoint joinPoint) throws Throwable
	{
		Object retVal = null;
		try
		{
			if (log.isDebugEnabled()) {
				log.debug("Enter into {}.{}() with argument[s] = {}"+ joinPoint.getSignature().getDeclaringTypeName()+
						joinPoint.getSignature().getName()+ Arrays.toString(joinPoint.getArgs()));
			} else {
				log.info("Enter into {}.{}()"+ joinPoint.getTarget().getClass().getName()+ joinPoint.getSignature().getName());
			}
			retVal = joinPoint.proceed();
		}
		finally
		{
			if (log.isDebugEnabled()) {
				log.debug("Exit from {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
			} else {
				log.info("Exit from {}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName());
			}
		}
		return retVal;
	}

}
