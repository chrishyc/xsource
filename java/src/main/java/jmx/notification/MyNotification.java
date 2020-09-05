package jmx.notification;

import jmx.mxbean.Book;
import jmx.mxbean.HelloMXBean;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class MyNotification extends NotificationBroadcasterSupport implements HelloMXBean {
    private int seq = 0;
    
    public void sayHello() {
        System.out.println("hello, world");
        Notification notify =
                //通知名称；谁发起的通知；序列号；发起通知时间；发送的消息
                new Notification("jack.hi", this, ++seq, System.currentTimeMillis(), "jack");
        sendNotification(notify);
    }
    
    public int add(int x, int y) {
        return x + y;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getCacheSize() {
        return this.cacheSize;
    }
    
    public synchronized void setCacheSize(int size) {
        int oldSize = this.cacheSize;
        this.cacheSize = size;
        
        System.out.println("Cache size now " + this.cacheSize);
        //构建通知
        Notification n = new AttributeChangeNotification(this,
                sequenceNumber++, System.currentTimeMillis(),
                "CacheSize changed", "CacheSize", "int", oldSize,
                this.cacheSize);
        //发送通知
        sendNotification(n);
    }
    
    @Override
    public Book getBook() {
        return null;
    }
    
    @Override
    public void addBook(Book book) {
    
    }
    
    //返回这个MBean将会发送的通知类型信息
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{AttributeChangeNotification.ATTRIBUTE_CHANGE};
        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name,
                description);
        return new MBeanNotificationInfo[]{info};
    }
    
    private final String name = "Reginald";
    private int cacheSize = DEFAULT_CACHE_SIZE;
    private static final int DEFAULT_CACHE_SIZE = 200;
    
    private long sequenceNumber = 1;
}
