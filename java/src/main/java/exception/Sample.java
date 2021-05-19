package exception;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Sample {
//    @Test
    public static void main(String[] args) {
        IntStream.rangeClosed(1, 1).forEach(i -> {
            long start = System.currentTimeMillis();
            try {
                new TalkService().say();
            } catch (Exception e) {
                System.out.println("==================printStackTrace==================");
                String message ="";
    
                for(StackTraceElement stackTraceElement : e.getStackTrace()) {
                    message = message + System.lineSeparator() + stackTraceElement.toString();
                }
                System.out.println(message);
                System.out.println("==================getMessage=======================");
                System.out.println(e.getMessage());
                System.out.println("====================toString=======================");
                System.out.println(e.toString());
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        });
    }
}
