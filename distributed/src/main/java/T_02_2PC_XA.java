import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.jdbc.MysqlXAConnection;
import com.mysql.cj.jdbc.MysqlXid;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

public class T_02_2PC_XA {
    public static void main(String[] args) throws Exception {
        
        // 是否开启日志
        boolean logXaCommands = true;
        
        // 获取账户库的 rm（ap做的事情）
        Connection accountConn = DriverManager.getConnection("jdbc:mysql://106.12.12.xxxx:3306/xa_account?useUnicode=true&characterEncoding=utf8", "root", "xxxxx");
        XAConnection accConn = new MysqlXAConnection((JdbcConnection) accountConn, logXaCommands);
        XAResource accountRm = accConn.getXAResource();
        // 获取红包库的RM
        Connection redConn = DriverManager.getConnection("jdbc:mysql://106.12.12.xxxx:3306/xa_red_account?useUnicode=true&characterEncoding=utf8", "root", "xxxxxx");
        XAConnection Conn2 = new MysqlXAConnection((JdbcConnection) redConn, logXaCommands);
        XAResource redRm = Conn2.getXAResource();
        // XA 事务开始了
        // 全局事务
        byte[] globalId = UUID.randomUUID().toString().getBytes();
        // 就一个标识
        int formatId = 1;
        
        // 账户的分支事务
        byte[] accBqual = UUID.randomUUID().toString().getBytes();
        ;
        Xid xid = new MysqlXid(globalId, accBqual, formatId);
        
        // 红包分支事务
        byte[] redBqual = UUID.randomUUID().toString().getBytes();
        ;
        Xid xid1 = new MysqlXid(globalId, redBqual, formatId);
        try {
            // 账号事务开始 此时状态：ACTIVE
            accountRm.start(xid, XAResource.TMNOFLAGS);
            // 模拟业务
            String sql = "update account set balance_amount=balance_amount-90 where user_id=1";
            PreparedStatement ps1 = accountConn.prepareStatement(sql);
            ps1.execute();
            accountRm.end(xid, XAResource.TMSUCCESS);
            // 账号 XA 事务 此时状态：IDLE
            // 红包分支事务开始
            redRm.start(xid1, XAResource.TMNOFLAGS);
            // 模拟业务
            String sql1 = "update account set balance_amount=balance_amount-10 where user_id=1";
            PreparedStatement ps2 = redConn.prepareStatement(sql1);
            ps2.execute();
            redRm.end(xid1, XAResource.TMSUCCESS);
            
            
            // 第一阶段：准备提交
            int rm1_prepare = accountRm.prepare(xid);
            int rm2_prepare = redRm.prepare(xid1);
            
            //  XA 事务 此时状态：PREPARED
            // 第二阶段：TM 根据第一阶段的情况决定是提交还是回滚
            boolean onePhase = false; //TM判断有2个事务分支，所以不能优化为一阶段提交
            if (rm1_prepare == XAResource.XA_OK && rm2_prepare == XAResource.XA_OK) {
                accountRm.commit(xid, onePhase);
                redRm.commit(xid1, onePhase);
            } else {
                accountRm.rollback(xid);
                redRm.rollback(xid1);
            }
            
        } catch (Exception e) {
            // 出现异常，回滚
            accountRm.rollback(xid);
            redRm.rollback(xid1);
            e.printStackTrace();
        }
    }
}
