package com.xsource.serviceregistry.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class DeleteNote_curator {

    // 创建会话
    public static void main(String[] args) throws Exception {


        //不使用fluent编程风格

        RetryPolicy exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("127.0.0.1:2181", exponentialBackoffRetry);
        curatorFramework.start();
        System.out.println( "会话被建立了");

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


        // 删除节点
        String path = "/lg-curator";
        client.delete().deletingChildrenIfNeeded().withVersion(-1).forPath(path);

        System.out.println("删除成功，删除的节点" + path);



    }


}
