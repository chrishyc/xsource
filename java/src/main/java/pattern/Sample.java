package pattern;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sample {
    
    @Test
    public void testDatetime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM01");
        System.out.println(format.format(new Date()));
    }
    
    @Test
    public void testReplace(){
        System.out.println("hello.world.i.am.chris".replaceAll("\\.","\\/"));
    }
    
    @Test
    public void testOr() {
        System.out.println(".a/b".replaceAll("[./]", "_"));
    }
    
    @Test
    public void testChar() {
        int c = 2;
        char d = (char) (c + '0');
        System.out.println(d);
    }
}
