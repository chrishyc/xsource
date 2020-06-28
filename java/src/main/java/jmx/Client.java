package jmx;

import jmx.mbean.HelloMBean;
import jmx.mxbean.HelloMXBean;
import jmx.notification.MyNotification;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Set;

public class Client {
    //构建一个监听器类，该类需要实现NotificationListener接口并实现handleNotification方法
    public static class ClientListener implements NotificationListener {
        @Override
        public void handleNotification(Notification notification, Object handback) {
            echo("\nReceived notification:");
            echo("\tClassName: " + notification.getClass().getName());
            echo("\tSource: " + notification.getSource());
            echo("\tType: " + notification.getType());
            echo("\tMessage: " + notification.getMessage());
            //如果通知类型是AttributeChangeNotification，那么就获取一些和属性有关的信息
            if (notification instanceof AttributeChangeNotification) {
                AttributeChangeNotification acn = (AttributeChangeNotification) notification;
                echo("\tAttributeName: " + acn.getAttributeName());
                echo("\tAttributeType: " + acn.getAttributeType());
                echo("\tNewValue: " + acn.getNewValue());
                echo("\tOldValue: " + acn.getOldValue());
            }
        }
    }
    
    public static void main(String[] args) throws IOException, MalformedObjectNameException, InstanceNotFoundException, InterruptedException {
        echo("\nCreate an RMI connector client and " +
                "connect it to the RMI connector server");
        //构造并获取RMI连接
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:60940/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        
        //获取MBeanServer的连接
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        waitForEnterPressed();
        
        echo("\nMBeanServer default domain = " + mbsc.getDefaultDomain());
        echo("\nMBean count = " + mbsc.getMBeanCount());
        echo("\nQuery MBeanServer MBeans:");
        
        Set<ObjectName> objectNames = mbsc.queryNames(null, null);
        for (ObjectName objectName : objectNames) {
            echo("\tObjectName = " + objectName);
        }
        waitForEnterPressed();
        
        //创建监听器
        NotificationListener listener = new ClientListener();
        
        //管理 Hello MBean
        ObjectName mbeanName = new ObjectName("jmx:type=MyNotification");
        HelloMXBean mbeanProxy = JMX.newMBeanProxy(mbsc, mbeanName, HelloMXBean.class, true);
        echo("\nAdd notification listener...");
        mbsc.addNotificationListener(mbeanName, listener, null, null);
        
        echo("\nCacheSize = " + mbeanProxy.getCacheSize());
        mbeanProxy.setCacheSize(150);
        echo("\nWaiting for notification...");
        Thread.sleep(2000);
        echo("\nCacheSize = " + mbeanProxy.getCacheSize());
        echo("\nInvoke sayHello() in Hello MBean...");
        mbeanProxy.sayHello();
        echo("\nInvoke add(2, 3) in Hello MBean...");
        mbeanProxy.add(2, 3);
        
        waitForEnterPressed();
        
        //关闭客户端
        echo("\nClose the connection to the server");
        jmxc.close();
        echo("\nBye! Bye!");
    }
    
    
    private static void echo(String msg) {
        System.out.println(msg);
    }
    
    private static void waitForEnterPressed() {
        try {
            echo("\nPress <Enter> to continue...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
