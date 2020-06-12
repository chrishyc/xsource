package netty.demo.consumer;


import netty.UserService;

public class ClientBootStrap {
    
    public static final String providerName = "UserService#sayHello#";
    
    public static void main(String[] args) throws InterruptedException {
        
        RpcConsumer rpcConsumer = new RpcConsumer();
        UserService proxy = (UserService) rpcConsumer.createProxy(UserService.class, providerName);
        
        while (true) {
            Thread.sleep(2000);
            System.out.println(proxy.sayHello("alan walker,faded"));
        }
        
        
    }
    
    
}
