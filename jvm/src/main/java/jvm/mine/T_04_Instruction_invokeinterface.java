package jvm.mine;


import org.junit.Test;

/**
 *
 */
public class T_04_Instruction_invokeinterface implements Runnable{
    
    @Test
    public void testInvokeinterface() {
        Runnable o = new T_04_Instruction_invokeinterface();
        o.run();
    }
    
    @Override
    public void run() {
    
    }
}
