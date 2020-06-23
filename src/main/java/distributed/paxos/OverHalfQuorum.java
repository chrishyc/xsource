package distributed.paxos;

import java.util.Set;

/**
 *
 */
public class OverHalfQuorum {
    int half;
    
    // n表示集群中zkServer的个数（准确的说是参与者的个数，参与者不包括观察者节点）
    public OverHalfQuorum(int n) {
        this.half = n / 2;
    }
    
    // 验证是否符合过半机制
    public boolean containsQuorum(Set<Long> set) {
        // half是在构造方法里赋值的
        // set.size()表示某台zkServer获得的票数
        return (set.size() > half);
    }
    
}
