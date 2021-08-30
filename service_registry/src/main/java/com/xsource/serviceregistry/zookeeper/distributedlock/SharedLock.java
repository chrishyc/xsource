package com.xsource.serviceregistry.zookeeper.distributedlock;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.Charset;

public class SharedLock {
    private static ZooKeeper zooKeeper = null;
    
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                try {
                    String sharedLock1 = zooKeeper.create("/shared_lock", "shared_lock".getBytes(Charset.defaultCharset()),
                            ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                    String sharedLock2 = zooKeeper.create("/shared_lock", "shared_lock".getBytes(Charset.defaultCharset()),
                            ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                    System.out.println("sharedLock1:" + sharedLock1 + ",sharedLock2:" + sharedLock2);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }
}
