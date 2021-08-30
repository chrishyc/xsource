package com.xsource.serviceregistry.zookeeper.nameservice;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NameClient {
    private static ZooKeeper zooKeeper = null;
    
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String prefix = "/test";
        
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                try {
                    List<String> children = zooKeeper.getChildren(prefix, e -> {
                    });
                    System.out.println("children name:" + Arrays.toString(new List[]{children}));
                    children.stream().iterator().forEachRemaining(name -> {
                        try {
                            byte[] data = zooKeeper.getData(prefix + "/" + name, event1 -> {
                            }, null);
                            System.out.println(new String(data));
                        } catch (KeeperException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }
}
