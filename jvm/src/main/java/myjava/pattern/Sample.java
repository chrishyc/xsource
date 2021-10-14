package myjava.pattern;

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
    public void testReplace() {
        System.out.println("hello/world.i.am/chris".replace("/", "_").replace(".", "_"));
    }

    @Test
    public void testOr() {
        System.out.println(".a/b".replaceAll("[./]", "_"));
        System.out.println("t1hello".matches("tj|c3|c4"));
    }

    @Test
    public void testChar() {
        int c = 2;
        char d = (char) (c + '0');
        System.out.println(d);
    }
}
