package aop.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Target2 {
    
    @Autowired
    private Target3 target3;
    
    public void transfer() {
//        throw new RuntimeException("no error");
        Map<Integer, List<Integer>> out = new HashMap<>();
        Iterator<Map.Entry<Integer, List<Integer>>> it = out.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Integer>> entry = it.next();
        }
        Queue<int[]> minQ = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
    }
    
}
