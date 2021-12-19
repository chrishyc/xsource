package open.ioc.instantiat;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

public class T_01_InstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanName.equals("a")) {
            return "a to T_01_InstantiationAwareBeanPostProcessor";
        }
        return null;
    }
}
