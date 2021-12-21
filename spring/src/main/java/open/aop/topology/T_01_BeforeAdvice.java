package open.aop.topology;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class T_01_BeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before say");
    }
}
