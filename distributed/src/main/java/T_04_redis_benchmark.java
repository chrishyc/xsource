import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * z_04_分布式_redis_01_基准测试_c10k.md
 */
public class T_04_redis_benchmark {
    public static void main(String[] args) throws Exception {
    
    }
    
    public int nextBeautifulNumber(int n) {
        for (int i = n + 1; i < Integer.MAX_VALUE; i++) {
            if (ans(i)) return i;
        }
        return -1;
    }
    
    public boolean ans(int i) {
        int[] array = new int[10];
        while (i > 0) {
            array[i % 10]++;
            i = i / 10;
        }
        for (int k = 0; k < array.length; k++) {
            if (array[k] != 0 && array[k] != k) return false;
        }
        return true;
    }
    
    public int countHighestScoreNodes(int[] parents) {
        Map<Integer, List<Integer>> tree = new HashMap<>();
        for (int i = 0; i < parents.length; i++) {
            if (!tree.containsKey(parents[i])) {
                tree.put(parents[i], new ArrayList<>());
            }
            if (!tree.containsKey(i)) {
                tree.put(i, new ArrayList<>());
            }
            tree.get(parents[i]).add(i);
        }
        int[] sum = new int[parents.length];
        dfs(tree, 0, sum);
        System.out.println(sum[0]);
        int maxAns = 0;
        int count = 0;
        for (int i = 0; i < parents.length; i++) {
            int part0 = sum[0] - sum[i];
            part0 = part0 == 0 ? 1 : part0;
            int partL = 1;
            if (tree.get(i).size() >= 1) {
                partL = sum[tree.get(i).get(0)];
            }
            int partR = 1;
            if (tree.get(i).size() >= 2) {
                partR = sum[tree.get(i).get(1)];
            }
            if (maxAns < part0 * partL * partR) {
                count = 1;
                maxAns = part0 * partL * partR;
            } else if (maxAns == part0 * partL * partR) {
                count++;
            }
        }
        System.out.println(maxAns);
        return count;
    }
    
    public int dfs(Map<Integer, List<Integer>> tree, int i, int[] sum) {
        List<Integer> sub = tree.get(i);
        int left = 0;
        int right = 0;
        if (sub.size() >= 1) left = dfs(tree, sub.get(0), sum);
        if (sub.size() >= 2) right = dfs(tree, sub.get(1), sum);
        return sum[i] = left + right + 1;
    }
}
