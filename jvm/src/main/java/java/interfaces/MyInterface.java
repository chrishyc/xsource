package java.interfaces;

/**
 * [https://stackoverflow.com/questions/42780642/why-this-keyword-is-used-in-java-interface-and-what-does-it-refer]
 * why this keyword is used in java interface and what does it refer?
 * <p>
 * "this" represents the new Instance which implements the interface
 * <p>
 * One thing you are missing is, that the this keyword represents the current "Object"
 * and not current "Class". So, if and when you create an object of this "Interface"
 * (by implementing it in another class of course), the this keyword will represent that specific object.
 * <p>
 * javap -p -v -l /Users/chris/xsource/java/src/main/java/interfaces/MyInterface.class > /Users/chris/xsource/java/src/main/java/interfaces/MyInterface.load
 *
 * @param <T>
 */
public interface MyInterface<T> {
    default void testDefault() {
        String a = "123456";
        System.out.println(a);
        
        System.out.println(this.getClass());
    }
    
    default void testDefault2() {
        System.out.println(this);
    }
    
    static void testStatic() {
        String a = "123456";
        System.out.println(a);
    }
    
    interface InnerInterface {
        void innerMethod();
        
        default void innerDefault() {
        }
    }
}
