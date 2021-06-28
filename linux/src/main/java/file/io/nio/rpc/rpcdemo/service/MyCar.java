package file.io.nio.rpc.rpcdemo.service;

/**
 * @author: 马士兵教育
 * @create: 2020-08-16 21:16
 */
public class MyCar implements Car {

    @Override
    public String ooxx(String msg) {
        return "server res "+ msg;
    }

    @Override
    public Persion oxox(String name, Integer age) {
        Persion p = new Persion();
        p.setName(name);
        p.setAge(age);
        return p;
    }
}
