package concurrent;

import java.util.Arrays;

public class T25_Collection_Array {
    public static void main(String[] args) {
        int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Arrays.stream(a).map(i -> i + 1).forEach(i -> System.out.print(i + " "));
    }
}
