package myjava.serialization.serializable;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class SerialDemo {
  public static final String PATH = "/Users/chris/xsource/java/src/main/java/serialization/serializable/";

    /**
     * {@link ObjectOutputStream#writeObject0}
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //序列化
        FileOutputStream fos = new FileOutputStream(PATH + "object.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        User user1 = new User("chris", "123456", "male");
        oos.writeObject(user1);
        oos.flush();
        oos.close();
        //反序列化
        FileInputStream fis = new FileInputStream(PATH + "object.out");
        ObjectInputStream ois = new ObjectInputStream(fis);
        User user2 = (User) ois.readObject();
        System.out.println(user2.getUserName() + " " +
                user2.getPassword() + " " + user2.getSex());
        //反序列化的输出结果为：chris 123456 male
    }
}
