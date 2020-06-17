package zookeeper.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

public class Get_NoteChildren {


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

        // 获取子节点列表
        List<String> children = zkClient.getChildren("/lg-zkclient");
        System.out.println(children);

        // 注册监听事件

        /*
            客户端可以对一个不存在的节点进行子节点变更的监听
            只要该节点的子节点列表发生变化，或者该节点本身被创建或者删除，都会触发监听
         */
        zkClient.subscribeChildChanges("/lg-zkclient-get", new IZkChildListener() {

            /*
                s : parentPath
                list : 变化后子节点列表
             */

            @Override
            public void handleChildChange(String parentPath, List<String> list) throws Exception {
                System.out.println(parentPath + "的子节点列表发生了变化,变化后的子节点列表为"+ list);

            }
        });

        //测试
        zkClient.createPersistent("/lg-zkclient-get");
        Thread.sleep(1000);

        zkClient.createPersistent("/lg-zkclient-get/c1");
        Thread.sleep(1000);



    }

}
