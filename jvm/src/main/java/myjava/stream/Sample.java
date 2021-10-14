package myjava.stream;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Sample {
    @Test
    public void testRange() {
        int result = IntStream.rangeClosed(1, 10).reduce((i, j) -> i * j).getAsInt();
        System.out.println(result);
        List<Integer> list = new LinkedList<>();
        IntStream.rangeClosed(1, 10).forEach(list::add);
    }

}
