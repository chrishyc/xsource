package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AnnotationAdvice {
    @Pointcut("execution(* aop.Target2.transfer(..))")
    public void pointcut() {

    }

    @Before(value = "pointcut()")
    public void before() {
        System.out.println("before:当前时间:" + System.currentTimeMillis());
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturn(Object result) {
        System.out.println("afterReturn:当前时间:" + System.currentTimeMillis());

    }

    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrowing(RuntimeException e) {
        System.out.println("afterThrowing:当前时间:" + System.currentTimeMillis());

    }

    @After(value = "pointcut()")
    public void after() {
        System.out.println("after:当前时间:" + System.currentTimeMillis());

    }

    @Around(value = "pointcut()")
    public void round(ProceedingJoinPoint p) throws Throwable {
        System.out.println("round:当前时间:" + System.currentTimeMillis());
        p.proceed();
    }
}
