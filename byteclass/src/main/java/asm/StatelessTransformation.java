package asm;

public class StatelessTransformation {
    public void before() throws Exception {
        Thread.sleep(100);
    }

    public void after() throws Exception {
        long begin = System.currentTimeMillis();
        Thread.sleep(100);
        long after = System.currentTimeMillis();
    }
}
