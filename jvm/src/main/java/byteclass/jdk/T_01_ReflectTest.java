package byteclass.jdk;

import java.lang.reflect.InvocationTargetException;

public class T_01_ReflectTest {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        //注意一定要返回接口，不能返回实现类否则会报错
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        water water = (water) DynamicAgent.agent(water.class, new Cool());
        Fruit fruit = (Fruit) DynamicAgent.agent(Fruit.class, new Apple());
        water.drink();
        fruit.show();
    }
}
