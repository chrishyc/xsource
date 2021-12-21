package open.aop.pojo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Component
public class Target2 implements InitializingBean {
    
    @PostConstruct
    public void init() {
        System.out.println("-----target2 init-----");
    }
    
    public void transfer() {
//        throw new RuntimeException("no error");
        Map<Integer, List<Integer>> out = new HashMap<>();
        Iterator<Map.Entry<Integer, List<Integer>>> it = out.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Integer>> entry = it.next();
        }
        Queue<int[]> minQ = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
    
    }
}
