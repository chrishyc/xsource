package byteclass.resolvabletype;

import org.junit.Test;
import org.springframework.core.ResolvableType;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class Sample<E> {
    private HashMap<ArrayList<E>, E> myMap;
    
    @Test
    public void testResolvableType() throws NoSuchFieldException {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        ResolvableType t = ResolvableType.forField(getClass().getDeclaredField("myMap"));
        ResolvableType superType = t.getSuperType();
        System.out.println(superType.resolve());
        ResolvableType resolvableType = t.asMap();
        System.out.println(resolvableType.resolve());
        t.getGeneric(0).resolve(); // Integer
        t.getGeneric(1).resolve(); // List
        t.getGeneric(1); // List&lt;String&gt;
        Class<?> aClass = t.resolveGeneric(1, 0);// String
    }
    
    @Test
    public void testType() {
        Stream.of(getClass().getDeclaredMethods())
                .filter(method -> method.getName().contains("method"))
                .forEach(method -> Stream.of(method.getGenericParameterTypes()).forEach(param -> {
                    if (param instanceof ParameterizedType) {
                        System.out.println("=========" + param + "=========");
                        Stream.of(((ParameterizedType) param).getActualTypeArguments()).map(Type::getClass).forEach(System.out::println);
                    } else if (param instanceof GenericArrayType) {
                    } else if (param instanceof TypeVariable) {
                    } else if (param instanceof WildcardType) {
                    } else if (param instanceof Class) {
                        System.out.println("=========" + param + "=========");
                    }
                }));
    }
    
    public E method(ArrayList<ArrayList<String>> al1, ArrayList<E> al2, ArrayList<String> al3,
                    ArrayList<? extends Number> al4, ArrayList<E[]> al5, ArrayList<ArrayList<E>[]> al6,
                    ArrayList<ArrayList> al7, int la8, Integer la9) {
        return null;
    }
}
