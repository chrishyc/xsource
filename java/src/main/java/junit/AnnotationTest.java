package junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(MyRunner.class)
public class AnnotationTest {
    
    private int i = 0;
    
    @Before
    public void setup() {
        i = 1;
    }
    
    @Test
    public void test1() {
        assertEquals(1, i);
        System.out.println("hello test1");
    }
    
    @Test
    public void test2() {
        assertEquals(1, i);
        System.out.println("hello test2");
    }
    
    @Ignore
    public void testIgnore() {
        assertEquals(1, 199);
        System.out.println("hello ignore");
    }
    
    @After
    public void after() {
        i = 99;
    }
}
