package concurrent;

import java.util.*;

public class T25_Collection_set_Set {
    public static void main(String[] args) {
        Collection<Integer> c1 = new ArrayList();
        c1.add(1);
        c1.add(2);
        c1.add(3);
        c1.stream().forEach(System.out::println);
        
        List<Integer> c2 = new ArrayList<>();
        Set<Integer> c3 = new HashSet<>();
        Queue<Integer> c4 = new LinkedList<>();
        
    }
}
