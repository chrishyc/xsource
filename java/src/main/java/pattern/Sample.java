package pattern;

import org.junit.Test;

public class Sample {
    @Test
    public void testOr(){
        System.out.println(".a/b".replaceAll("[./]", "_"));
    }
}
