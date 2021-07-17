package junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Vector;

public class Junit4RawTest extends TestCase {
    protected Vector fEmpty;
    protected Vector fFull;
    
    public Junit4RawTest(String name) {
        super(name);
    }
    
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite1());
    }
    
    protected void setUp() {
        fEmpty = new Vector();
        fFull = new Vector();
        fFull.addElement(new Integer(1));
        fFull.addElement(new Integer(2));
        fFull.addElement(new Integer(3));
    }
    
    public static Test suite() {
        return new TestSuite(Junit4RawTest.class);
    }
    
    public static Test suite1() {
        TestSuite suite = new TestSuite();
        suite.addTest(new Junit4RawTest("testCapacity"));
        suite.addTest(new Junit4RawTest("testClone"));
        return suite;
    }
    
    public void testCapacity() {
        int size = fFull.size();
        for (int i = 0; i < 100; i++)
            fFull.addElement(new Integer(i));
        assertTrue(fFull.size() == 100 + size);
    }
    
    public void testClone() {
        Vector clone = (Vector) fFull.clone();
        assertTrue(clone.size() == fFull.size());
        assertTrue(clone.contains(new Integer(1)));
    }
    
    public void testContains() {
        assertTrue(fFull.contains(new Integer(1)));
        assertTrue(!fEmpty.contains(new Integer(1)));
    }
    
    public void testElementAt() {
        Integer i = (Integer) fFull.elementAt(0);
        assertTrue(i.intValue() == 1);
        try {
            fFull.elementAt(fFull.size());
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        fail("Should raise an ArrayIndexOutOfBoundsException");
    }
    
    public void testRemoveAll() {
        fFull.removeAllElements();
        fEmpty.removeAllElements();
        assertTrue(fFull.isEmpty());
        assertTrue(fEmpty.isEmpty());
    }
    
    public void testRemoveElement() {
        fFull.removeElement(new Integer(3));
        assertTrue(!fFull.contains(new Integer(3)));
    }
}
