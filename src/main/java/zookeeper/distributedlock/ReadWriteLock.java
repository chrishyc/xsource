package zookeeper.distributedlock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 只监听前一个node,避免群羊效应
 */
public class ReadWriteLock {
    private static final String LOCK_NODE_PARENT_PATH = "/share_lock";
    
    /**
     * 自旋测试超时阈值，考虑到网络的延时性，这里设为1000毫秒
     */
    private static final long spinForTimeoutThreshold = 1000L;
    
    private static final long SLEEP_TIME = 100L;
    
    private ZooKeeper zooKeeper;
    
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);
    
    private ReadLock readLock = new ReadLock();
    
    private WriteLock writeLock = new WriteLock();
    
    private Comparator<String> nameComparator;
    
    public ReadWriteLock() throws Exception {
        Watcher watcher = event -> {
            if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                connectedSemaphore.countDown();
            }
        };
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 1000, watcher);
        connectedSemaphore.await();
        
        nameComparator = (x, y) -> {
            Integer xs = getSequence(x);
            Integer ys = getSequence(y);
            return xs.compareTo(ys);
        };
    }
    
    public DistributedLock readLock() {
        return readLock;
    }
    
    public DistributedLock writeLock() {
        return writeLock;
    }
    
    public interface DistributedLock {
        
        void lock() throws Exception;
        
        Boolean tryLock() throws Exception;
        
        Boolean tryLock(long millisecond) throws Exception;
        
        void unlock() throws Exception;
    }
    
    public enum LockStatus {
        TRY_LOCK,
        LOCKED,
        UNLOCK
    }
    
    class ReadLock implements DistributedLock, Watcher {
        
        private LockStatus lockStatus = LockStatus.UNLOCK;
        
        private CyclicBarrier lockBarrier = new CyclicBarrier(2);
        
        private String prefix = new Random(System.nanoTime()).nextInt(10000000) + "-read-";
        
        private String name;
        
        @Override
        public void lock() throws Exception {
            if (lockStatus == LockStatus.LOCKED) {
                return;
            }
            
            // 1. 创建锁节点
            if (name == null) {
                name = createLockNode(prefix);
                name = name.substring(name.lastIndexOf("/") + 1);
                System.out.println("创建锁节点 " + name);
            }
            
            // 2. 获取锁节点列表
            List<String> nodes = zooKeeper.getChildren(LOCK_NODE_PARENT_PATH, this);
            nodes.sort(nameComparator);
            
            // 3. 检查能否获取锁，若能，直接返回
            if (canAcquireLock(name, nodes)) {
                System.out.println(name + " 获取锁");
                lockStatus = LockStatus.LOCKED;
                return;
            }
            // 4. 不能获取锁，找到比自己小的最后一个的写锁节点，并监视
            int index = Collections.binarySearch(nodes, name, nameComparator);
            for (int i = index - 1; i >= 0; i--) {
                if (nodes.get(i).contains("write")) {
                    zooKeeper.exists(LOCK_NODE_PARENT_PATH + "/" + nodes.get(i), this);
                    break;
                }
            }
            
            // 5. 等待监视的节点被删除
            lockStatus = LockStatus.TRY_LOCK;
            lockBarrier.await();
        }
        
        @Override
        public Boolean tryLock() throws Exception {
            if (lockStatus == LockStatus.LOCKED) {
                return true;
            }
            
            // 1. 创建锁节点
            if (name == null) {
                name = createLockNode(prefix);
                name = name.substring(name.lastIndexOf("/") + 1);
                System.out.println("创建锁节点 " + name);
            }
            
            // 2. 获取锁节点列表
            List<String> nodes = zooKeeper.getChildren(LOCK_NODE_PARENT_PATH, null);
            nodes.sort(nameComparator);
            
            // 3. 检查能否获取锁
            if (canAcquireLock(name, nodes)) {
                System.out.println(name + " 获取锁");
                lockStatus = LockStatus.LOCKED;
                return true;
            }
            
            return false;
        }
        
        @Override
        public Boolean tryLock(long millisecond) throws Exception {
            long millisTimeout = millisecond;
            if (millisTimeout <= 0L) {
                return false;
            }
            
            final long deadline = System.currentTimeMillis() + millisTimeout;
            for (; ; ) {
                if (tryLock()) {
                    return true;
                }
                
                if (millisTimeout > spinForTimeoutThreshold) {
                    Thread.sleep(SLEEP_TIME);
                }
                
                millisTimeout = deadline - System.currentTimeMillis();
                if (millisTimeout <= 0L) {
                    return false;
                }
            }
        }
        
        @Override
        public void unlock() throws Exception {
            if (lockStatus == LockStatus.UNLOCK) {
                return;
            }
            
            deleteLockNode(name);
            lockStatus = LockStatus.UNLOCK;
            lockBarrier.reset();
            System.out.println(name + " 释放锁");
            name = null;
        }
        
        @Override
        public void process(WatchedEvent event) {
            if (Event.KeeperState.SyncConnected != event.getState()) {
                return;
            }
            
            if (Event.EventType.None == event.getType() && event.getPath() == null) {
                connectedSemaphore.countDown();
            } else if (Event.EventType.NodeDeleted == event.getType()) {
                if (lockStatus != LockStatus.TRY_LOCK) {
                    return;
                }
                
                System.out.println(name + " 获取锁");
                lockStatus = LockStatus.LOCKED;
                try {
                    lockBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    class WriteLock implements DistributedLock, Watcher {
        
        private LockStatus lockStatus = LockStatus.UNLOCK;
        
        private CyclicBarrier lockBarrier = new CyclicBarrier(2);
        
        private String prefix = new Random(System.nanoTime()).nextInt(1000000) + "-write-";
        
        private String name;
        
        @Override
        public void lock() throws Exception {
            if (lockStatus == LockStatus.LOCKED) {
                return;
            }
            
            // 1. 创建锁节点
            if (name == null) {
                name = createLockNode(prefix);
                name = name.substring(name.lastIndexOf("/") + 1);
                System.out.println("创建锁节点 " + name);
            }
            
            // 2. 获取锁节点列表
            List<String> nodes = zooKeeper.getChildren(LOCK_NODE_PARENT_PATH, null);
            nodes.sort(nameComparator);
            
            // 3. 检查自己是否是排在第一位，若是，加锁成功
            if (isFirstNode(name, nodes)) {
                System.out.println(name + " 获取锁");
                lockStatus = LockStatus.LOCKED;
                return;
            }
            
            // 4. 若不是，定位到上一个锁节点，并监视
            int index = Collections.binarySearch(nodes, name, nameComparator);
            zooKeeper.exists(LOCK_NODE_PARENT_PATH + "/" + nodes.get(index - 1), this);
            
            // 5. 等待监视的节点被删除
            lockStatus = LockStatus.TRY_LOCK;
            lockBarrier.await();
        }
        
        @Override
        public Boolean tryLock() throws Exception {
            if (lockStatus == LockStatus.LOCKED) {
                return true;
            }
            
            // 1. 创建锁节点
            if (name == null) {
                name = createLockNode(prefix);
                name = name.substring(name.lastIndexOf("/") + 1);
                System.out.println("创建锁节点 " + name);
            }
            
            // 2. 获取锁节点列表
            List<String> nodes = zooKeeper.getChildren(LOCK_NODE_PARENT_PATH, null);
            nodes.sort(nameComparator);
            
            // 3. 检查自己是否是排在第一位，若是，加锁成功
            if (isFirstNode(name, nodes)) {
                System.out.println(name + " 获取锁");
                lockStatus = LockStatus.LOCKED;
                return true;
            }
            
            return false;
        }
        
        @Override
        public Boolean tryLock(long millisecond) throws Exception {
            long millisTimeout = millisecond;
            if (millisTimeout <= 0L) {
                return false;
            }
            
            final long deadline = System.currentTimeMillis() + millisTimeout;
            for (; ; ) {
                if (tryLock()) {
                    return true;
                }
                
                if (millisTimeout > spinForTimeoutThreshold) {
                    Thread.sleep(SLEEP_TIME);
                }
                
                millisTimeout = deadline - System.currentTimeMillis();
                if (millisTimeout <= 0L) {
                    return false;
                }
            }
        }
        
        @Override
        public void unlock() throws Exception {
            if (lockStatus == LockStatus.UNLOCK) {
                return;
            }
            
            System.out.println(name + " 释放锁");
            deleteLockNode(name);
            lockStatus = LockStatus.UNLOCK;
            lockBarrier.reset();
            name = null;
        }
        
        @Override
        public void process(WatchedEvent event) {
            if (Event.KeeperState.SyncConnected != event.getState()) {
                return;
            }
            
            if (Event.EventType.None == event.getType() && event.getPath() == null) {
                connectedSemaphore.countDown();
            } else if (Event.EventType.NodeDeleted == event.getType()) {
                if (lockStatus != LockStatus.TRY_LOCK) {
                    return;
                }
                
                lockStatus = LockStatus.LOCKED;
                try {
                    lockBarrier.await();
                    System.out.println(name + " 获取锁");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private Integer getSequence(String name) {
        return Integer.valueOf(name.substring(name.lastIndexOf("-") + 1));
    }
    
    private String createLockNode(String name) {
        String path = null;
        try {
            path = zooKeeper.create(LOCK_NODE_PARENT_PATH + "/" + name, "".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        
        return path;
    }
    
    private void deleteLockNode(String name) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(LOCK_NODE_PARENT_PATH + "/" + name, false);
        zooKeeper.delete(LOCK_NODE_PARENT_PATH + "/" + name, stat.getVersion());
    }
    
    private Boolean canAcquireLock(String name, List<String> nodes) {
        if (isFirstNode(name, nodes)) {
            return true;
        }
        
        Map<String, Boolean> map = new HashMap<>();
        boolean hasWriteoperation = false;
        for (String n : nodes) {
            if (n.contains("read") && !hasWriteoperation) {
                map.put(n, true);
            } else {
                hasWriteoperation = true;
                map.put((n), false);
            }
        }
        
        return map.get(name);
    }
    
    private Boolean isFirstNode(String name, List<String> nodes) {
        return nodes.get(0).equals(name);
    }
}
