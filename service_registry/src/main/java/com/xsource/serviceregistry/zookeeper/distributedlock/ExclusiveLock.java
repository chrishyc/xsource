package com.xsource.serviceregistry.zookeeper.distributedlock;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.Charset;

public class ExclusiveLock {
    private static ZooKeeper zooKeeper = null;
    
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                try {
                    String exclusiveLock1 = zooKeeper.create("/exclusive_lock", "exclusive_lock".getBytes(Charset.defaultCharset()),
                            ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                    String exclusiveLock2 = zooKeeper.create("/exclusive_lock", "exclusive_lock".getBytes(Charset.defaultCharset()),
                            ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                    System.out.println("exclusiveLock1:" + exclusiveLock1 + ",exclusiveLock2:" + exclusiveLock2);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }
}
