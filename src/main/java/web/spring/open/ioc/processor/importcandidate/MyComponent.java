package web.spring.open.ioc.processor.importcandidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import web.spring.open.ioc.cycle.TestBean;

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
