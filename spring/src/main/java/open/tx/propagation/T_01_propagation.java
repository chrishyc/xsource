package open.tx.propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * MySQL的官方文档中有明确的说明不支持嵌套事务
 * <p>
 * 1.Propagation.REQUIRED 扁平事务。在这种事务中，所有操作都处于同一层次。主要缺点是不能提交或回滚事务的某一部
 * <p>
 * 2.带有保存点的扁平事务。允许在事务执行过程中回滚到同一事务中较早的一个状态
 * <p>
 * 3.PROPAGATION_NESTED，嵌套事务由savepoint实现
 * <p>
 * 4.分布式事务
 */
@Component
public class T_01_propagation {
    
    @Autowired
    private T_01_propagationB propagationB;
    
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void methodB() {
        propagationB.methodA(); //4
    }
    
    private void updateData() {
    
    }
    
    private void insertData() {
    
    }
    
    public void testRequired() {
    
    }
}
