package jmx;

import jmx.mbean.Hello;
import jmx.mxbean.HelloMX;
import jmx.notification.MyNotification;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
/**
 * refer: https://www.cnblogs.com/dongguacai/p/5900507.html
 * refer: http://www.tianshouzhi.com/api/tutorials/jmx/34
 *
 * Java Management Extensions (JMX)用于监控和管理CPU,JVM等资源
 */
public class Agent {
    public static void main(String[] args)
            throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("jmx:type=Hello");
        Hello mbean = new Hello();
        ObjectName nameMX = new ObjectName("jmx:type=HelloMX");

        HelloMX mxBean = new HelloMX();
        ObjectName notiName = new ObjectName("jmx:type=MyNotification");
        MyNotification notificationBean = new MyNotification();
        mbs.registerMBean(notificationBean, notiName);
        mbs.registerMBean(mbean, name);
        mbs.registerMBean(mxBean, nameMX);
        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
