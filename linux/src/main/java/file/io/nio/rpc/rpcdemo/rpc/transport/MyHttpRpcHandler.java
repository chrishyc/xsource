package file.io.nio.rpc.rpcdemo.rpc.transport;


import file.io.nio.rpc.rpcdemo.rpc.Dispatcher;
import file.io.nio.rpc.rpcdemo.rpc.protocol.MyContent;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: 马士兵教育
 * @create: 2020-09-06 21:47
 */
public class MyHttpRpcHandler extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream in = req.getInputStream();
        ObjectInputStream oin = new ObjectInputStream(in);
        try {
            MyContent myContent = (MyContent)oin.readObject();

            String serviceName = myContent.getName();
            String method = myContent.getMethodName();
            Object c = Dispatcher.getDis().get(serviceName);
            Class<?> clazz = c.getClass();
            Object res = null;
            try {


                Method m = clazz.getMethod(method, myContent.getParameterTypes());
                res = m.invoke(c, myContent.getArgs());


            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }


            MyContent resContent = new MyContent();
            resContent.setRes(res);
            if(res == null){
                System.out.println("完蛋了。。。");
            }
//                byte[] contentByte = SerDerUtil.ser(resContent);

            ServletOutputStream out = resp.getOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(resContent);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
