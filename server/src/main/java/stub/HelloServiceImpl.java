package stub;


import kie.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.*;
import java.rmi.*;

/**
 <p>There are eight ways to export remote objects:
 *
 * <li>Subclassing {@code UnicastRemoteObject} and calling the
 * {@link UnicastRemoteObject#UnicastRemoteObject()} constructor.
 *
 * <li>Subclassing {@code UnicastRemoteObject} and calling the
 * {@link UnicastRemoteObject#UnicastRemoteObject(int) UnicastRemoteObject(port)} constructor.
 *
 * <li>Subclassing {@code UnicastRemoteObject} and calling the
 * {@link UnicastRemoteObject#UnicastRemoteObject(int, RMIClientSocketFactory, RMIServerSocketFactory)
 * UnicastRemoteObject(port, csf, ssf)} constructor.
 *
 * <li>Calling the
 * {@link UnicastRemoteObject#exportObject(Remote)} method.
 * <strong>Deprecated.</strong>
 *
 * <li>Calling the
 * {@link UnicastRemoteObject#exportObject(Remote, int) exportObject(Remote, port)} method.
 *
 * <li>Calling the
 * {@link UnicastRemoteObject#exportObject(Remote, int, RMIClientSocketFactory, RMIServerSocketFactory)
 * exportObject(Remote, port, csf, ssf)} method.
 *
 * <li>Calling the
 * {@link UnicastRemoteObject#exportObject(Remote, int, ObjectInputFilter) exportObject(Remote, port, filter)} method.
 *
 * <li>Calling the
 * {@link UnicastRemoteObject#exportObject(Remote, int, RMIClientSocketFactory, RMIServerSocketFactory, ObjectInputFilter)
 * exportObject(Remote, port, csf, ssf, filter)} method.
 */
public class HelloServiceImpl extends UnicastRemoteObject implements IHelloService {
    
    
    public HelloServiceImpl() throws RemoteException {
       super();
    }
    
    @Override
    public String sayHello(User user) throws RemoteException {
        System.out.println("this is server, hello:" + user.getName());
        return "success";
    }
}
