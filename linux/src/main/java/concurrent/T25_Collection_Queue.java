package concurrent;

import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class T25_Collection_Queue {
    public static void main(String[] args) {
        Queue<Integer> q = new ArrayBlockingQueue<>(2);
        q.add(0);
        q.add(1);
        q.add(2);
        q.add(3);
        System.out.println(q);
    }
    
    @Test
    public void testAdd(){
        Queue<Integer> q = new ArrayBlockingQueue<>(2);
        q.add(0);
        q.add(1);
        q.add(2);
        q.add(3);
    }
    
    @Test
    public void testOffer(){
        Queue<Integer> q = new ArrayBlockingQueue<>(2);
        q.offer(0);
        q.offer(1);
        q.offer(2);
        q.offer(3);
    }
    
    
    @Test
    public void testPut() throws InterruptedException {
        BlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.put(0);
        q.put(1);
        q.put(2);
        q.put(3);
    }
    
    
    @Test
    public void testRemove(){
        Queue<Integer> q = new ArrayBlockingQueue<>(2);
        q.add(0);
        q.add(1);
        q.add(2);
        q.add(3);
    }
    
    @Test
    public void testPoll(){
        Queue<Integer> q = new ArrayBlockingQueue<>(2);
        q.poll();
        q.poll();
        q.poll();
        q.poll();
    }
}
