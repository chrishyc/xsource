package file.io.nio.rpc.refactor.client;

import file.io.nio.rpc.refactor.proxymode.rpcprotocol.RpcProxyUtil;
import file.io.nio.rpc.refactor.rpcenv.serverenv.ServerRPCEnv;
import file.io.nio.rpc.refactor.service.Car;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 马士兵教育
 * @create: 2020-07-12 20:08
 */

/*
    1，先假设一个需求，写一个RPC
    2，来回通信，连接数量，拆包？
    3，动态代理呀，序列化，协议封装
    4，连接池
    5，就像调用本地方法一样去调用远程的方法，面向java中就是所谓的 面向interface开发
 */

public class MyRPCTest {

    //多多包涵，如果一会翻车，请不要打脸。。。。。

    @Test
    public void get() {

        final AtomicInteger num = new AtomicInteger(0);

        //启动服务器
        new Thread(() -> {
            ServerRPCEnv.startServer();
        }).start();

        System.out.println("server started......");


        //模拟串行请求
//        for (int i = 0; i < 10; i++) {
//            System.out.println("----");
//            Car car = RpcProxyUtil.proxyGet(Car.class);
//            String res = car.ooxx("hello" + num.incrementAndGet());
//            System.out.println(res);
//        }

        //模拟并发请求
        int size = 50;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new Thread(() -> {
                Car car = RpcProxyUtil.proxyGet(Car.class);//动态代理实现
                String msg = "helloa" + num.incrementAndGet();
                String ooxx = car.ooxx(msg);
                System.out.println(ooxx + " from ..." + msg);
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}


