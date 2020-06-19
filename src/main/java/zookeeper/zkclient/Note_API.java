package zookeeper.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class Note_API {


    /*
        借助zkclient完成会话的创建
     */
    public static void main(String[] args) throws InterruptedException {

        /*
            创建一个zkclient实例就可以完成连接，完成会话的创建
            serverString : 服务器连接地址

            注意：zkClient通过对zookeeperAPI内部封装，将这个异步创建会话的过程同步化了..
         */

        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("会话被创建了..");

        // 判断节点是否存在
        String path = "/lg-zkClient-Ep";
        boolean exists = zkClient.exists(path);

        if(!exists){
            // 创建临时节点
            zkClient.createEphemeral(path,"123");
        }

        // 读取节点内容
        Object o = zkClient.readData(path);
        System.out.println(o);


        // 注册监听
        zkClient.subscribeDataChanges(path, new IZkDataListener() {

            /*
                当节点数据内容发生变化时，执行的回调方法
                s: path
                o: 变化后的节点内容
             */
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(s+"该节点内容被更新，更新的内容"+o);
            }

            /*
                当节点被删除时，会执行的回调方法
                s : path
             */
            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s+"该节点被删除");
            }
        });


        // 更新节点内容
        zkClient.writeData(path,"456");
        Thread.sleep(1000);

        // 删除节点
        zkClient.delete(path);
        Thread.sleep(1000);


    }

}
