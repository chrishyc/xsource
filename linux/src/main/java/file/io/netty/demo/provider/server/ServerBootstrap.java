package file.io.netty.demo.provider.server;


import file.io.netty.demo.provider.service.UserServiceImpl;

public class ServerBootstrap {
    
    public static void main(String[] args) throws InterruptedException {
        
        UserServiceImpl.startServer("127.0.0.1", 8990);
        
        
    }
    
    
}
