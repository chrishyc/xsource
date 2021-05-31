package junit;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.ExternalResource;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertEquals;

//@RunWith(MyRunner.class)
public class Junit4Test {
    public static void main(String[] args) {
        JUnitCore.runClasses(Junit4Test.class);
    }
    
    private int i = 0;
    
    @Rule
    public ExpectedException exp = ExpectedException.none();
    
    @ClassRule
    public static ExternalResource external = new ExternalResource()
    {
        protected void before() throws Throwable
        {
            System.out.println("Perparing test data.");
            System.out.println("Test data is Ready!");
        }
        
        protected void after()
        {
            System.out.println("Cleaning test data.");
        }
    };
    
    @BeforeClass
    public static void beforeClassM()
    {
        
        System.out.println("<<Before Class>>");
    }
    
    @Before
    public void setup() {
        exp.expect(IndexOutOfBoundsException.class);
        i = 1;
    }
    
    @Test(timeout = 200, expected = AssertionError.class)
    public void test1() {
        assertEquals(1, i);
        exp.expectMessage("Hello World");
        System.out.println("hello test1");
    }
    
    @CalTime
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
    @AfterClass
    public static void afterClassM()
    {
        System.out.println("<<After Class>>");
    }
    
    @After
    public void after() {
        i = 99;
    }
}
