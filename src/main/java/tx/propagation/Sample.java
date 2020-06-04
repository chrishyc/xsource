package tx.propagation;

import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * MySQL的官方文档中有明确的说明不支持嵌套事务
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
        
        @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        void required(){
        
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
    
    @Test
    public void testRequired(){
    
    }
}
