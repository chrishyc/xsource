package mvc;

import org.junit.Test;

public class Sample {
    /**父子webApplicationContext
     * 1.{@link org.springframework.web.context.ContextLoaderListener#contextInitialized}
     * 会监听ServletContext初始化回调，查看是否有contextClass，有则加载。此处的contextClass为父webApplicationContext
     *
     * 2.{@link org.springframework.web.servlet.DispatcherServlet}父类
     * {@link org.springframework.web.servlet.FrameworkServlet#createWebApplicationContext}会生成一个
     * 子类webApplicationContext，即XmlWebApplicationContext
     *
     */
    @Test
    public void testInitWebContext(){
    
    }
}
