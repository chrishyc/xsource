package web;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterServerCenterImpl implements RegisterServerCenter{
    //以哈希表的形式存储注册的远程调用函数
    private static Map<String, Class> serviceRegister = new HashMap<>();

    //远程调用端口号
    private int port;

    //创建一个定长线程池，可控制线程最大并发数
    //java.lang.Runtime.availableProcessors() 方法返回到Java虚拟机的可用的处理器数量
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //程序启动和关闭的标记
    private static boolean isRunning = false;

    public RegisterServerCenterImpl(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        ServerSocket serverSocket = null;
        Socket socket;
        try {
            serverSocket = new ServerSocket();

            //与ServerSocket与指定端口号绑定
            serverSocket.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRunning = true;
        while (true) {
            try {
                System.out.println("---- start server ----");

                //等待请求
                socket = serverSocket.accept();

                //启动线程完成请求
                executorService.execute(new ServiceTask(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void stop() {
        isRunning = false;

        //关闭线程池
        executorService.shutdown();
    }

    @Override
    public void register(Class service, Class serviceImpl) {

        //将可远程调用注册到map中
        serviceRegister.put(service.getName(), serviceImpl);
    }

    private static class ServiceTask implements Runnable {

        private Socket socket;

        ServiceTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ObjectOutputStream outputStream = null;
            ObjectInputStream inputStream = null;
            try {
                //接收到请求
                inputStream = new ObjectInputStream(socket.getInputStream());

                //获取类名、方法名、参数类型、参数值
                String serviceName = inputStream.readUTF();
                String methodName = inputStream.readUTF();
                Class[] paramType = (Class[]) inputStream.readObject();
                Object[] args = (Object[]) inputStream.readObject();

                //通过服务注册表，获取类、获取方法，执行方法获取结果
                Class serviceClass = serviceRegister.get(serviceName);
                Method method = serviceClass.getMethod(methodName, paramType);
                Object result = method.invoke(serviceClass.newInstance(), args);

                //返回结果
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(result);
            } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
