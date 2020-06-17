package zookeeper.api;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GetNoteData implements Watcher {

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

         zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetNoteData());
        System.out.println(zooKeeper.getState());

        // 计数工具类：CountDownLatch:不让main方法结束，让线程处于等待阻塞
        //countDownLatch.await();\
        Thread.sleep(Integer.MAX_VALUE);

    }



    /*
        回调方法：处理来自服务器端的watcher通知
     */
    public void process(WatchedEvent watchedEvent) {

        /*
            子节点列表发生改变时，服务器端会发生noteChildrenChanged事件通知
            要重新获取子节点列表，同时注意：通知是一次性的，需要反复注册监听
         */
        if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged){

            List<String> children = null;
            try {
                children = zooKeeper.getChildren("/lg-persistent", true);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(children);

        }



        // SyncConnected
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){

            //解除主程序在CountDownLatch上的等待阻塞
            System.out.println("process方法执行了...");

            // 获取节点数据的方法
            try {
                getNoteData();

                // 获取节点的子节点列表方法
                getChildrens();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }

    /*
        获取某个节点的内容
     */
    private void getNoteData() throws KeeperException, InterruptedException {

        /**
         * path    : 获取数据的路径
         * watch    : 是否开启监听
         * stat    : 节点状态信息
         *        null: 表示获取最新版本的数据
         *  zk.getData(path, watch, stat);
         */
        byte[] data = zooKeeper.getData("/lg-persistent", false, null);
        System.out.println(new String(data));


    }


    /*
        获取某个节点的子节点列表方法
     */
    public static void getChildrens() throws KeeperException, InterruptedException {

        /*
            path:路径
            watch:是否要启动监听，当子节点列表发生变化，会触发监听
            zooKeeper.getChildren(path, watch);
         */
        List<String> children = zooKeeper.getChildren("/lg-persistent", true);
        System.out.println(children);



    }



}
