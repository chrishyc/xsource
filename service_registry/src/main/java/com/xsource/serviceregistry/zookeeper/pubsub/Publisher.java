package com.xsource.serviceregistry.zookeeper.pubsub;

import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.Charset;

public class Publisher {
    private static ZooKeeper zookeeper = null;
    
    public static void main(String[] args) throws Exception {
//        zooKeeper();
//        zkClient();
        curator();
    }
    
    public static void zooKeeper() throws IOException, InterruptedException {
        zookeeper = new ZooKeeper("127.0.0.1:2181", 5000, event -> {
            if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                try {
//                    String persistent = zookeeper.create("/pubsub", "订阅发布".getBytes(Charset.defaultCharset()), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//                    System.out.println("persistent:" + persistent);
//                    String child = zookeeper.create("/pubsub/child", "订阅发布".getBytes(Charset.defaultCharset()), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    String child2 = zookeeper.create("/pubsub/child3", "订阅发布".getBytes(Charset.defaultCharset()), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                    
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }
    
    /**
     * {@link ZkClient#retryUntilConnected}同步等待实现同步调用
     * zkClient.create
     */
    public static void zkClient() throws InterruptedException {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        zkClient.create("/pubsub/child3", "hello zookeeper", CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }
    
    public static void curator() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(5000, 4);
        CuratorFramework curator = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        curator.start();
        curator.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath("/pubsub/child3");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
