package zookeeper.pubsub;

import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Subscriber {
    private static ZooKeeper zookeeper = null;
    
    public static void main(String[] args) throws Exception {
//        zooKeeper();
//        zkClient();
        curator();
    }
    
    public static void zooKeeper() throws IOException, InterruptedException {
        zookeeper = new ZooKeeper("127.0.0.1:2181", 5000, event -> {
            try {
                List<String> children = zookeeper.getChildren("/pubsub", false);
                System.out.println("children=" + children.toString());
                
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }
    
    /**
     * {@link ZkClient#retryUntilConnected}同步等待实现同步调用
     * zkClient.getChildren
     */
    public static void zkClient() throws InterruptedException {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        zkClient.subscribeChildChanges("/pubsub", (s, list) -> {
            System.out.println(Arrays.toString(new List[]{list}));
        });
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
        curator.getData().usingWatcher((Watcher) event -> {
            try {
                curator.getChildren()
                        .forPath("/pubsub");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }
}
