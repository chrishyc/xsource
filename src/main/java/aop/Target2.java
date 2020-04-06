package aop;

import org.springframework.stereotype.Component;

@Component
public class Target2 {
    public void transfer() {
        throw new RuntimeException("no error");
    }
}
