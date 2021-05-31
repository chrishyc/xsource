package junit;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class CalTestTimeExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create("CalTestTimeExtension");
    
    private static final String STORE_KEY = "calTime";
    
    @Override
    public void beforeTestExecution(ExtensionContext context) {
        //判断是否有添加对应的注解
        if (!isSupport(context)) {
            return;
        }
        context.getStore(NAMESPACE).put(STORE_KEY, System.currentTimeMillis());
    }
    
    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (!isSupport(context))
            return;
        
        long beginTime = (long) context.getStore(NAMESPACE).get(STORE_KEY);
        long executeTime = System.currentTimeMillis() - beginTime;
        System.out.println("Test " + context.getDisplayName() +" execute cost " + executeTime + " ms");
        
    }
    
    private static boolean isSupport(ExtensionContext context) {
        return context.getElement()
                .map(el -> el.isAnnotationPresent(CalTime.class))
                .orElse(false);
    }
}
