package tx.propagation;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * MySQL的官方文档中有明确的说明不支持嵌套事务
 *
 * 在上面的1，将开起新事务A，2的时候会插入数据，此时事务A挂起，没有commit，3的时候，
 * 使用PROPAGATION_NESTED传播，将在3点的时候新建一个savepoint保存2插入的数据，不提交。
 *
 *
 * 如果methodB出现异常，将回滚4的操作，不影响2的操作，同时可以处理后面的5,6逻辑，最后一起commit: 2,5,6
 * 如果methodB没有出现异常，那么将一起commit: 2,4,6。
 * 假如methodB使用的PROPAGATION_REQUIRES_NEW，那么B异常，会commit: 2,5,6，和NESTED一致，如果methodB
 * 没有出现异常，那么会先commit4，再commit:6，那么事务将分离开，不能保持一致，假如执行6报错，2和6将回滚，
 * 而4却没有被回滚，不能达到预期效果。
 *
 * 1.Propagation.REQUIRED 扁平事务。在这种事务中，所有操作都处于同一层次。主要缺点是不能提交或回滚事务的某一部
 *
 * 2.带有保存点的扁平事务。允许在事务执行过程中回滚到同一事务中较早的一个状态
 *
 * 3.PROPAGATION_NESTED，嵌套事务由savepoint实现
 *
 * 4.分布式事务
 */
public class Sample {
    class ServiceA {
        @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
            // 1
        void methodA() {
            insertData(); //2
            try {
                new ServiceB().methodB();   //3
            } catch (Exception e) {
                // 执行其他业务, 如 ServiceC.methodC();   //5
            }
            updateData(); //6
        }
    }

    class ServiceB {

        @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
        void methodB() {
            updateData(); //4
        }
    }

    private void updateData() {

    }

    private void insertData() {

    }
}
