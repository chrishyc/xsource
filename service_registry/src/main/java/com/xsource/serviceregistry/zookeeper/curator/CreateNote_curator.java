package com.xsource.serviceregistry.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class CreateNote_curator {

    // 创建会话
    public static void main(String[] args) throws Exception {


        //不使用fluent编程风格

        RetryPolicy exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);

        // 使用fluent编程风格
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(30000)
                .retryPolicy(exponentialBackoffRetry)
                .namespace("base")  // 独立的命名空间 /base
                .build();

        client.start();

        System.out.println("会话2创建了");

        // 创建节点
        String path = "/lg-curator/c1";
        String s = client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT).forPath(path, "init".getBytes());

        System.out.println("节点递归创建成功，该节点路径" + s);


    }


}
