package com.xsource.serviceregistry.zookeeper.api;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class UpdateNoteData implements Watcher {

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

         zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new UpdateNoteData());
        System.out.println(zooKeeper.getState());

        // 计数工具类：CountDownLatch:不让main方法结束，让线程处于等待阻塞
        //countDownLatch.await();\
        Thread.sleep(Integer.MAX_VALUE);

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

            // 更新数据节点内容的方法
            try {
                updateNoteSync();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
    
        }


    }

    /*
        更新数据节点内容的方法
     */
    private void updateNoteSync() throws KeeperException, InterruptedException {

         /*
            path:路径
            data:要修改的内容 byte[]
            version:为-1，表示对最新版本的数据进行修改
            zooKeeper.setData(path, data,version);
         */


        byte[] data = zooKeeper.getData("/lg-persistent", false, null);
        System.out.println("修改前的值：" + new String(data));

        //修改/lg-persistent 的数据 stat: 状态信息对象
        Stat stat = zooKeeper.setData("/lg-persistent", "客户端修改了节点数据".getBytes(), -1);

        byte[] data2 = zooKeeper.getData("/lg-persistent", false, null);
        System.out.println("修改后的值：" + new String(data2));

    }


}
