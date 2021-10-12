package jvm;

import java.util.LinkedList;
import java.util.List;

public class T05_gc_HeapOOM {
    public static void main(String[] args) {
        List<Object> objects = new LinkedList<>();
        for (; ; ) objects.add(new Object());
    }
}
