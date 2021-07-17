package jmx.metrics;


import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.stream.IntStream;


public class OS {
    public static void main(String[] args) {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        IntStream.range(1, 100).forEach((i) -> System.out.println(operatingSystemMXBean.getProcessCpuLoad()));
    }
}
