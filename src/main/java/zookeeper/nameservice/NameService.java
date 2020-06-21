package zookeeper.nameservice;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * @author chris
 */
public class NameService {
    private static ZooKeeper zooKeeper = null;
    
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String prefix = "/test";
        String suffix = "/" + NameService.class.getName();
        
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                try {
                    zooKeeper.create(prefix,
                            "10.1.2.10".getBytes(Charset.defaultCharset()), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    zooKeeper.create(prefix + suffix,
                            "10.1.2.10".getBytes(Charset.defaultCharset()), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    List<String> children = zooKeeper.getChildren(prefix, e -> {
                    });
                    System.out.println(Arrays.toString(new List[]{children}));
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        zooKeeper.delete(prefix, -1);
                        zooKeeper.delete(prefix + suffix, -1);
                    } catch (InterruptedException | KeeperException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }
}
