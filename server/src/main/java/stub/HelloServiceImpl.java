package stub;


import pojo.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 远程服务对象实现类：必须要继承UnicastRemoteObject
 * @author chris
 */
public class HelloServiceImpl extends UnicastRemoteObject implements IHelloService {
    
    
    public HelloServiceImpl() throws RemoteException {
    }
    
    @Override
    public String sayHello(User user) throws RemoteException {
        System.out.println("this is server, hello:" + user.getName());
        return "success";
    }
}
