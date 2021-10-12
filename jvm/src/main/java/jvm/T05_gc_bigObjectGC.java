package jvm;

public class T05_gc_bigObjectGC {
    public static void main(String[] args) {
        
        for(int i=0; i<10000; i++) {
            byte[] b = new byte[1024 * 1024];
        }
    }
}
