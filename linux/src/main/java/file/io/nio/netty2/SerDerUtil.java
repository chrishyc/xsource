package file.io.nio.netty2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author: 马士兵教育
 * @create: 2020-07-19 21:14
 */
public class SerDerUtil {
    static ByteArrayOutputStream out = new ByteArrayOutputStream();

    public synchronized  static byte[] ser(Object msg){
        out.reset();
        ObjectOutputStream oout = null;
        byte[] msgBody = null;
        try {
            oout = new ObjectOutputStream(out);
            oout.writeObject(msg);
            msgBody= out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return msgBody;


    }
}
