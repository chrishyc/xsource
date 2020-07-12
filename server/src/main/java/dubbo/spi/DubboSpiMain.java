package dubbo.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.Set;
import org.apache.dubbo.common.extension.factory.*;
import org.apache.dubbo.common.extension.*;
import org.apache.dubbo.common.extension.Adaptive;
/**
 * 1.ExtensionLoader用于spi加载扩展类
 * 2.首先通过spi加载org.apache.dubbo.common.extension.ExtensionFactory.
 * 3.通过spi加载指定接口同名的META-INF/interface
 * 4.每个接口对应一个{@link ExtensionLoader},包含spi文件中的所有类实现
 * 5.objectFactory对应{@link AdaptiveExtensionFactory}
 * 6.每个{@link ExtensionLoader}都有cachedDefaultName,对应spi文件中有{@link SPI}的{@link SPI#value()}
 * 7.{@link Adaptive}
 */
public class DubboSpiMain {
    public static void main(String[] args) {
        // 获取扩展加载器
        ExtensionLoader<HelloService> extensionLoader = ExtensionLoader.getExtensionLoader(HelloService.class);
        // 遍历所有的支持的扩展点 META-INF.dubbo
        Set<String> extensions = extensionLoader.getSupportedExtensions();
        for (String extension : extensions) {
            String result = extensionLoader.getExtension(extension).sayHello();
            System.out.println(result);
        }
        
    }
}
