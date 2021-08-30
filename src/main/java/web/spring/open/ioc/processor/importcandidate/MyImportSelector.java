package web.spring.open.ioc.processor.importcandidate;

import ioc.processor.importcandidate.MyConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
/**
 * 工具类,不会注入容器
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{MyConfiguration.class.getName()};
    }
}
