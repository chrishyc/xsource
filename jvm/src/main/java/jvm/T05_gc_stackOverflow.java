package jvm;

public class T05_gc_stackOverflow {
    public static void main(String[] args) {
        m();
    }
    
    static void m() {
        m();
    }
}
