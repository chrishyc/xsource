package configuration;

import org.junit.Test;
import org.springframework.core.ResolvableType;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Sample<E> {
    private HashMap<Integer, List<String>> myMap;
    
    @Test
    public void testResolvableType() throws NoSuchFieldException {
        ResolvableType t = ResolvableType.forField(getClass().getDeclaredField("myMap"));
        ResolvableType superType = t.getSuperType();// AbstractMap&lt;Integer, List&lt;String&gt;&gt;
        ResolvableType resolvableType = t.asMap();// Map&lt;Integer, List&lt;String&gt;&gt;
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
                        System.out.println("param:" + param + ",type:ParameterizedType");
                    } else if (param instanceof GenericArrayType) {
                        System.out.println("param:" + param + ",type:GenericArrayType");
                    } else if (param instanceof TypeVariable) {
                        System.out.println("param:" + param + ",type:TypeVariable");
                    } else if (param instanceof WildcardType) {
                        System.out.println("param:" + param + ",type:WildcardType");
                    } else if (param instanceof Class) {
                        System.out.println("param:" + param + ",type:Class");
                    }
                }));
    }
    
    public E method(ArrayList<ArrayList<String>> al1, ArrayList al2, ArrayList<String> al3, ArrayList<? extends Number> al4, ArrayList[] al5, int a) {
        return null;
    }
}
