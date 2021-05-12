package exception;

import org.junit.Test;

import java.util.stream.IntStream;

public class Sample {
    @Test
    public void test() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            long start = System.currentTimeMillis();
            try {
                new TalkService().say();
            } catch (Exception e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        });
    }
}
