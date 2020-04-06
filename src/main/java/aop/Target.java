package aop;

public class Target {
    public void transfer() {
        throw new RuntimeException("no error");
    }
}
