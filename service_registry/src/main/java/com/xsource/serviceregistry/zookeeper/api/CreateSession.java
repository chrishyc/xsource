package com.xsource.serviceregistry.zookeeper.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CreateSession implements Watcher {

    private  static CountDownLatch countDownLatch = new CountDownLatch(1);


    /*
      建立会话
     */
    public static void main(String[] args) throws IOException, InterruptedException {

     /*
        客户端可以通过创建一个zk实例来连接zk服务器
        new Zookeeper(connectString,sesssionTimeOut,Wather)
        connectString: 连接地址：IP：端口
        sesssionTimeOut：会话超时时间：单位毫秒
        Wather：监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
     */

        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new CreateSession());
        System.out.println(zooKeeper.getState());

        // 计数工具类：CountDownLatch:不让main方法结束，让线程处于等待阻塞
        countDownLatch.await();

        System.out.println("客户端与服务端会话真正建立了");

    }

    /*
        回调方法：处理来自服务器端的watcher通知
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        // SyncConnected
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){

            //解除主程序在CountDownLatch上的等待阻塞
            System.out.println("process方法执行了...");
            countDownLatch.countDown();

        }


    }
}
