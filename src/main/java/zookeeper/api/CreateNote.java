package zookeeper.api;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CreateNote implements Watcher {

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

         zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNote());
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
            // 创建节点
            try {
                createNoteSync();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
    
        }


    }


    /*
       创建节点的方法
   */
    private static void createNoteSync() throws KeeperException, InterruptedException {

        /**
         *  path        ：节点创建的路径
         *  data[]      ：节点创建要保存的数据，是个byte类型的
         *  acl         ：节点创建的权限信息(4种类型)
         *                 ANYONE_ID_UNSAFE    : 表示任何人
         *                 AUTH_IDS    ：此ID仅可用于设置ACL。它将被客户机验证的ID替换。
         *                 OPEN_ACL_UNSAFE    ：这是一个完全开放的ACL(常用)--> world:anyone
         *                 CREATOR_ALL_ACL  ：此ACL授予创建者身份验证ID的所有权限
         *  createMode    ：创建节点的类型(4种类型)
         *                  PERSISTENT：持久节点
         *				    PERSISTENT_SEQUENTIAL：持久顺序节点
         *                  EPHEMERAL：临时节点
         *                  EPHEMERAL_SEQUENTIAL：临时顺序节点
         String node = zookeeper.create(path,data,acl,createMode);
         */

        // 持久节点
        String note_persistent = zooKeeper.create("/lg-persistent", "持久节点内容".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 临时节点
        String note_ephemeral = zooKeeper.create("/lg-ephemeral", "临时节点内容".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        // 持久顺序节点
        String note_persistent_sequential = zooKeeper.create("/lg-persistent_sequential", "持久顺序节点内容".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);

        System.out.println("创建的持久节点" + note_persistent);
        System.out.println("创建的临时节点" + note_ephemeral);
        System.out.println("创建的持久顺序节点" + note_persistent_sequential);

    }
}
