package jvm;

public class T05_gc_lambdaGC {
    public static void main(String[] args) {
        for(;;) {
            I i = C::n;
        }
    }
    
    public static interface I {
        void m();
    }
    
    public static class C {
        static void n() {
            System.out.println("hello");
        }
    }
}
