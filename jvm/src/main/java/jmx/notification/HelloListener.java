package jmx.notification;

import jmx.mbean.Hello;

import javax.management.Notification;
import javax.management.NotificationListener;

public class HelloListener implements NotificationListener {
    public void handleNotification(Notification notification, Object handback) {
        if (handback instanceof Hello) {
            Hello hello = (Hello) handback;
            hello.sayHello();
            System.out.println(notification.getMessage());
        }
    }
}
