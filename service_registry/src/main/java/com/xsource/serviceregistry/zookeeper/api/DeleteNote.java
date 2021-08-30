package com.xsource.serviceregistry.zookeeper.api;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class DeleteNote implements Watcher {

    private  static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zooKeeper;


    /*
      建立会话
     */
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

     /*
        客户端可以通过创建一个zk实例来连接zk服务器
        new Zookeeper(connectString,sesssionTimeOut,Wather)
        connectString: 连接地址：IP：端口
        sesssionTimeOut：会话超时时间：单位毫秒
        Wather：监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
     */

         zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new DeleteNote());
        System.out.println(zooKeeper.getState());

        // 计数工具类：CountDownLatch:不让main方法结束，让线程处于等待阻塞
        //countDownLatch.await();\
        Thread.sleep(Integer.MAX_VALUE);

    }



    /*
        回调方法：处理来自服务器端的watcher通知
     */
    public void process(WatchedEvent watchedEvent) {
        // SyncConnected
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){

            //解除主程序在CountDownLatch上的等待阻塞
            System.out.println("process方法执行了...");
            // 删除节点
            try {
                deleteNoteSync();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }

    /*
        删除节点的方法
     */
    private void deleteNoteSync() throws KeeperException, InterruptedException {

        /*
      	zooKeeper.exists(path,watch) :判断节点是否存在
      	zookeeper.delete(path,version) : 删除节点
      */

        Stat stat = zooKeeper.exists("/lg-persistent/c1", false);
        System.out.println(stat == null ? "该节点不存在":"该节点存在");

        if(stat != null){
            zooKeeper.delete("/lg-persistent/c1",-1);
        }

        Stat stat2 = zooKeeper.exists("/lg-persistent/c1", false);
        System.out.println(stat2 == null ? "该节点不存在":"该节点存在");



    }


}
