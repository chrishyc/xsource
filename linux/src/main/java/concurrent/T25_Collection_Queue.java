package concurrent;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class T25_Collection_Queue {
    public static void main(String[] args) {
        Queue<Integer> q = new ArrayBlockingQueue<>(2);
        q.add(0);
        q.add(1);
        q.add(2);
        q.add(3);
        System.out.println(q);
    }
}
