package tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * tomcat容器解析流程
 * {@link org.apache.catalina.startup.ContextConfig#webConfig}
 */
public class Sample {
    
    /**
     * 给tomcat服务器增加一个web工程
     * @throws LifecycleException
     */
    @Test
    public void textTomcat() throws LifecycleException {
        RestTemplate template = new RestTemplate();
        Tomcat tomcat = new Tomcat();
        File appDir = new File("/Users/chris/xsource/target/xsource-1.0-SNAPSHOT.war");
        tomcat.addWebapp(null, "/", appDir.getAbsolutePath());
        tomcat.start();
        String res = template.getForObject("http://localhost:8080/transferServlet", String.class);
        System.out.println(res);
    }
    
    /**
     * 起点{@link org.apache.catalina.startup.ContextConfig#parseWebXml}
     * <p>
     * The digester checks for structural correctness (eg single login-config)
     * This class checks for invalid duplicates (eg filter/servlet names)
     * StandardContext will check validity of values (eg URL formats etc)
     * <p>
     * {@link org.apache.catalina.deploy.ServletDef#addInitParameter(String, String)}
     * 代表tomcat中一个servlet的beanDefinition.
     * <p>
     * xml标签定义{@link org.apache.catalina.startup.WebRuleSet#addRuleInstances}
     * xml标签容器{@link org.apache.catalina.deploy.WebXml#toXml}
     */
    @Test
    public void testWebXml() {
    
    }
    
    /**
     *
     */
    
}
