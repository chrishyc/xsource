package demo;

public interface RegisterServerCenter {
    //服务启动
    void start();
    
    //服务终止
    void stop();
    
    //服务注册
    void register(Class service, Class serviceImpl);
}
