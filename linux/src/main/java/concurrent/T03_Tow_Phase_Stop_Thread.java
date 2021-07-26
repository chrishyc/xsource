package concurrent;

import org.junit.Test;

import java.io.IOException;


public class T03_Tow_Phase_Stop_Thread {
    //线程终止标志位
    volatile boolean terminated = false;
    boolean started = false;
    //采集线程
    Thread rptThread = new Thread(() -> {
        /**
         * run 方法逻辑中可能会存在无法及时响应线程的中断信号的操作。所以强烈建议自己实现中断控制的信号变量
         */
        while (!terminated) {
            //省略采集、回传实现
            report();
            //每隔两秒钟采集、回传一次数据
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " stop ,interrupt status:" + Thread.currentThread().isInterrupted());
                //重新设置线程中断状态
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " stop ,interrupt status:" + Thread.currentThread().isInterrupted());
            }
        }
        //执行到此处说明线程马上终止
        started = false;
    });
    
    //启动采集功能
    synchronized void start() {
        //不允许同时启动多个采集线程
        if (started) {
            return;
        }
        started = true;
        terminated = false;
        rptThread.start();
    }
    
    private void report() {
        System.out.println("report metrics");
    }
    
    //终止采集功能
    synchronized void stop() {
        //设置中断标志位
        terminated = true;
        //中断线程rptThread
        rptThread.interrupt();
    }
    
    @Test
    public void testThreadStop() throws InterruptedException, IOException {
        T03_Tow_Phase_Stop_Thread t = new T03_Tow_Phase_Stop_Thread();
        t.start();
        Thread.sleep(3000);
        t.stop();
        System.in.read();
    }
    
}
