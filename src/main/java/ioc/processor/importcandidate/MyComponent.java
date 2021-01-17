package ioc.processor.importcandidate;

import ioc.cycle.TestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 会注入容器
 * {@link ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForConfigurationClass}
 * registerBeanDefinitionForImportedConfigurationClass(configClass);
 */
@Component
public class MyComponent {
    @Autowired
    private TestBean testBean;
}
