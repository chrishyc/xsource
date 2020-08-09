package introspector;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class BeanInfoUtil {
    
    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setUserName("chris");
        BeanInfoUtil.getProperty(user, "userName");
        
        BeanInfoUtil.setProperty(user, "userName");
        
        BeanInfoUtil.getProperty(user, "userName");
        
        BeanInfoUtil.setPropertyByIntrospector(user, "userName");
        
        BeanInfoUtil.getPropertyByIntrospector(user, "userName");
        
        BeanInfoUtil.setProperty(user, "age");
        
    }
    
    public static void setProperty(User user, String userName) throws Exception {
        PropertyDescriptor propDesc = new PropertyDescriptor(userName, User.class);
        Method methodSetUserName = propDesc.getWriteMethod();
        methodSetUserName.invoke(user, "wong");
        System.out.println("set userName:" + user.getUserName());
    }
    
    public static void getProperty(User user, String userName) throws Exception {
        PropertyDescriptor proDescriptor = new PropertyDescriptor(userName, User.class);
        Method methodGetUserName = proDescriptor.getReadMethod();
        Object objUserName = methodGetUserName.invoke(user);
        System.out.println("get userName:" + objUserName.toString());
    }
    
    public static void setPropertyByIntrospector(User user, String userName) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
        PropertyDescriptor[] proDescriptors = beanInfo.getPropertyDescriptors();
        if (proDescriptors != null && proDescriptors.length > 0) {
            for (PropertyDescriptor propDesc : proDescriptors) {
                if (propDesc.getName().equals(userName)) {
                    Method methodSetUserName = propDesc.getWriteMethod();
                    methodSetUserName.invoke(user, "alan");
                    System.out.println("set userName:" + user.getUserName());
                    break;
                }
            }
        }
    }
    
    public static void getPropertyByIntrospector(User user, String userName) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
        PropertyDescriptor[] proDescriptors = beanInfo.getPropertyDescriptors();
        if (proDescriptors != null && proDescriptors.length > 0) {
            for (PropertyDescriptor propDesc : proDescriptors) {
                if (propDesc.getName().equals(userName)) {
                    Method methodGetUserName = propDesc.getReadMethod();
                    Object objUserName = methodGetUserName.invoke(user);
                    System.out.println("get userName:" + objUserName.toString());
                    break;
                }
            }
        }
    }
}
