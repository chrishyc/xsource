package suger;

import java.util.Arrays;
import java.util.List;

public class DeSuger {
    private void For() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
    }

    private void pre() {
        if (true) {
            System.out.println(1);
        } else {
            System.out.println(1);
        }
    }
}
