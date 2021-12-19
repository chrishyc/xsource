package open.ioc.instantiat;

import org.springframework.beans.factory.FactoryBean;

public class T_03_SupplierVo implements FactoryBean<T_02_FactoryBeanVo> {
    @Override
    public T_02_FactoryBeanVo getObject() throws Exception {
        return new T_02_FactoryBeanVo();
    }
    
    @Override
    public Class<?> getObjectType() {
        return T_02_FactoryBeanVo.class;
    }
}
