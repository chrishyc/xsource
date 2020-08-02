package interfaces;

public interface MyInterface {
    default void testDefault() {
        String a = "123456";
        System.out.println(a);
    }
    
    static void testStatic() {
        String a = "123456";
        System.out.println(a);
    }
}
