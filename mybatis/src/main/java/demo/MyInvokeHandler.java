package demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chris
 */
@SuppressWarnings("just for test")
public class MyInvokeHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String sql = "";
        if (method.isAnnotationPresent(SQL.class)) {
            SQL annotation = method.getAnnotation(SQL.class);
            sql = annotation.value();
        }


        return sql;
    }
}
