package concurrent;

import java.io.IOException;

public class T22_NormalReference {
    public static void main(String[] args) throws IOException {
        M m = new M();
        m = null;
        System.gc(); //DisableExplicitGC
        
        System.in.read();
    }
    
    public static class M {
        @Override
        protected void finalize() throws Throwable {
            System.out.println("finalize");
        }
    }
    
}
