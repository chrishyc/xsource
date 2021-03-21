package jdk;

import java.lang.reflect.InvocationTargetException;

public class ReflectTest {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        //注意一定要返回接口，不能返回实现类否则会报错
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        jdk.water water = (jdk.water) jdk.DynamicAgent.agent(jdk.water.class, new jdk.Cool());
        jdk.Fruit fruit = (jdk.Fruit) jdk.DynamicAgent.agent(jdk.Fruit.class, new jdk.Apple());
        water.drink();
        fruit.show();
    }
}
