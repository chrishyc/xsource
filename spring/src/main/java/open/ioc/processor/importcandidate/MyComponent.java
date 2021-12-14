package open.ioc.processor.importcandidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import open.ioc.cycle.T_Prototype;

/**
 * 会注入容器
 * {@link ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForConfigurationClass}
 * registerBeanDefinitionForImportedConfigurationClass(configClass);
 */
@Component
public class MyComponent {
    @Autowired
    private T_Prototype testBean;
}
