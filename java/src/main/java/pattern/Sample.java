package pattern;

import org.junit.Test;

public class Sample {
    @Test
    public void testOr(){
        System.out.println(".a/b".replaceAll("[./]", "_"));
    }
    
    @Test
    public void testChar(){
        int c=2;
        char d= (char) (c+'0');
        System.out.println(d);
    }
}
