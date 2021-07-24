package java.annotation;


import org.junit.Test;

import java.util.Arrays;

/**
 * 1.注解在哪用
 * 2.注解有效期
 * 3.注解继承关系
 */
public class Sample {
    @Test
    public void testCombinationAnnotation() {
        System.out.println(Arrays.toString(Combination.class.getAnnotations()));
    }
    
    public static void main(String[] args) {
        System.out.println(InheritedTest.class.isAnnotationPresent(SQL.class));
    }
}
