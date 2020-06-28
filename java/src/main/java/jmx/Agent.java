package jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Agent {
    public static void main(String[] args)
            throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("jmx:type=Hello");
        Hello mbean = new Hello();
        ObjectName nameMX = new ObjectName("jmx:type=HelloMX");
        
        HelloMX mxBean = new HelloMX();
        mbs.registerMBean(mbean, name);
        mbs.registerMBean(mxBean, nameMX);
        System.out.println("Waiting forever...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
