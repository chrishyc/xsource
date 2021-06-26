package file.io.nio.rpc.refactor.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author: 马士兵教育
 * @create: 2020-07-18 18:15
 */
public class SerDerTool {
    static ObjectOutputStream oout;
    static ByteArrayOutputStream out;
    static{
         out = new ByteArrayOutputStream();
    }

    public static synchronized byte[] objToByte(Object o){

        byte[] bytes = null;

        try {
            out.reset();
            oout = new ObjectOutputStream(out);
            oout.writeObject(o);
            bytes= out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
