package file.io.nio.rpc.rpcdemo.service;

import java.io.Serializable;

/**
 * @author: 马士兵教育
 * @create: 2020-08-16 22:24
 */
public class Persion implements Serializable {

    String name ;
    Integer age ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name + " " + age ;
    }
}
