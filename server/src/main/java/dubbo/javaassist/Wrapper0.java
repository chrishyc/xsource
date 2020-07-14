//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package dubbo.javaassist;

import demo.HelloService;
import javassist.CtClass;
import org.apache.dubbo.common.bytecode.ClassGenerator.DC;
import org.apache.dubbo.common.bytecode.NoSuchPropertyException;
import org.apache.dubbo.common.bytecode.Wrapper;
import org.apache.dubbo.common.bytecode.NoSuchMethodException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * {@link CtClass#debugWriteFile(String)}打印保存javaassist动态生成类
 */
public class Wrapper0 extends Wrapper implements DC {
    public static String[] pns;
    public static Map pts;
    public static String[] mns;
    public static String[] dmns;
    public static Class[] mts0;
    
    @Override
    public String[] getPropertyNames() {
        return pns;
    }
    
    @Override
    public boolean hasProperty(String var1) {
        return pts.containsKey(var1);
    }
    
    @Override
    public Class getPropertyType(String var1) {
        return (Class) pts.get(var1);
    }
    
    @Override
    public String[] getMethodNames() {
        return mns;
    }
    
    @Override
    public String[] getDeclaredMethodNames() {
        return dmns;
    }
    
    @Override
    public void setPropertyValue(Object var1, String var2, Object var3) {
        try {
            HelloService var4 = (HelloService) var1;
        } catch (Throwable var6) {
            throw new IllegalArgumentException(var6);
        }
        
        throw new NoSuchPropertyException("Not found property \"" + var2 + "\" field or setter method in class demo.HelloService.");
    }
    
    @Override
    public Object getPropertyValue(Object var1, String var2) {
        try {
            HelloService var3 = (HelloService) var1;
        } catch (Throwable var5) {
            throw new IllegalArgumentException(var5);
        }
        
        throw new NoSuchPropertyException("Not found property \"" + var2 + "\" field or setter method in class demo.HelloService.");
    }
    
    @Override
    public Object invokeMethod(Object var1, String var2, Class[] var3, Object[] var4) throws InvocationTargetException {
        HelloService var5;
        try {
            var5 = (HelloService) var1;
        } catch (Throwable var8) {
            throw new IllegalArgumentException(var8);
        }
        
        try {
            if ("sayHi".equals(var2) && var3.length == 1) {
                return var5.sayHi((String) var4[0]);
            }
        } catch (Throwable var9) {
            throw new InvocationTargetException(var9);
        }
        throw new NoSuchMethodException("Not found method \"" + var2 + "\" in class demo.HelloService.");
    }
    
    public Wrapper0() {
    }
}
