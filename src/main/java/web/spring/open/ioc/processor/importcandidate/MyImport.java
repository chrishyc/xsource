package web.spring.open.ioc.processor.importcandidate;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Import(value = {
        MyImportSelector.class,
        MyImportBeanDefinitionRegistrar.class,
        MyDeferredImportSelector.class,
        MyConfiguration.class,
        MyComponent.class})
@Component
public class MyImport {
}
