package introspector;

import java.awt.*;
import java.beans.*;

public class UserBeanInfo implements BeanInfo {
    @Override
    public BeanDescriptor getBeanDescriptor() {
        return null;
    }
    
    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return new EventSetDescriptor[0];
    }
    
    @Override
    public int getDefaultEventIndex() {
        return 0;
    }
    
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return new PropertyDescriptor[0];
    }
    
    @Override
    public int getDefaultPropertyIndex() {
        return 0;
    }
    
    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return new MethodDescriptor[0];
    }
    
    @Override
    public BeanInfo[] getAdditionalBeanInfo() {
        return new BeanInfo[0];
    }
    
    @Override
    public Image getIcon(int iconKind) {
        return null;
    }
}
