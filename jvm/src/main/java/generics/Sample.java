package generics;

import java.util.ArrayList;
import java.util.List;

public class Sample {
    public static void main(String[] args) {
        //类泛型
        ClassGenerics<String> classGenerics = new ClassGenerics<>();
        classGenerics.setData(Integer.toHexString(1));
        classGenerics.<String>setClazz(Integer.class);
        classGenerics.setClazzT(String.class);

        // 方法泛型
        List<String> names = new ArrayList<>();
        classGenerics.getAge(names);
        Number a = classGenerics.<String, Long>getAge("", 1L);


        // 方法类型推断
        ClassGenerics.<Animal>pick(new Animal(), new Dog());

        //通配符,相当于处理泛型的继承关系和多态关系
        List<Animal> animals = new ArrayList<>();
        ClassGenerics.countLegs(animals);

        List<Dog> dogs = new ArrayList<>();
        ClassGenerics.countLegs(dogs);


    }
}
