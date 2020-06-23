package distributed.paxos;

import java.util.Set;

/**
 * 过半机制:
 * 1.为什么需要过半机制?
 * 分布式环境无法保证全部节点正常返回，需要有一定容错且能保证分布式一致性的机制
 * 2.提出该机制后，为啥要限制过半?相等可以吗?
 * 不能相等.过半主要是为了避免脑裂.
 * 参考:https://juejin.im/post/5d36c2f25188257f6a209d37
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
