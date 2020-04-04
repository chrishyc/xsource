package annotation;


/**
 * 1.注解在哪用
 * 2.注解有效期
 * 3.注解继承关系
 */
public class Sample {
    public static void main(String[] args) {
        System.out.println(InheritedTest.class.isAnnotationPresent(SQL.class));
    }
}
