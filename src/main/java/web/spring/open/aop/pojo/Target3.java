package web.spring.open.aop.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Target3 {
    
    @Autowired
    private Target2 target2;
    public void transfer() {
//        throw new RuntimeException("no error");
    }
}
