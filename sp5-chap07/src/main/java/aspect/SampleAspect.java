package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

@Aspect
@Order(10)
public class SampleAspect {
	@Pointcut("execution(public * chap07..*(..))")
	private void publicTarget() {
	}
	
	@Around("execution(public * chap07..*(..))")
	@Order(30)
	public Object execute30(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("execute30");
		return joinPoint.proceed();
	}
	
	@Around("publicTarget()")
	@Order(25)
	public Object execute25(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("execute25");
		return joinPoint.proceed();
	}
	
	@Around("aspect.CommonPointcut.commonTarget()")
	@Order(35)
	public Object execute35(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("execute35");
		return joinPoint.proceed();
	}
}
