package jmx.metrics;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;
import java.util.List;
import java.util.stream.Stream;

public class Mem {
    
    public static void main(String[] args) {
        List<MemoryManagerMXBean> memBean = ManagementFactory.getMemoryManagerMXBeans();
        memBean.stream().flatMap(m -> Stream.of(m.getMemoryPoolNames())).forEach(System.out::println);
    }
}
