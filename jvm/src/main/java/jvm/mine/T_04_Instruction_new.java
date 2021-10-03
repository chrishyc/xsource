package jvm.mine;


import org.junit.Test;

/**
 * 属性表
 * Signature,记录泛型签名信息<String>
 * <p>
 * Signature: #108
 */
public class T_04_Instruction_new {
    
    /**
     * public void testNew();
     * descriptor: ()V
     * flags: ACC_PUBLIC
     * Code:
     * stack=2, locals=2, args_size=1
     * 0: new           #2                  // class java/lang/Object
     * 3: dup
     * 4: invokespecial #1                  // Method java/lang/Object."<init>":()V
     * 7: astore_1
     * 8: return
     * LineNumberTable:
     * line 13: 0
     * line 14: 8
     * LocalVariableTable:
     * Start  Length  Slot  Name   Signature
     * 0       9     0  this   Ljvm/mine/T_04_Instruction_Interpreter;
     * 8       1     1     o   Ljava/lang/Object;
     */
    public void testNew() {
        Object o = new Object();
    }
    
    @Test
    public void testNew_have_param() {
        Integer i = new Integer(1);
    }
    
    @Test
    public void testNeg() {
        System.out.println(Integer.toBinaryString(-3).length());
    }
    
    public int maxConsecutiveAnswers(String answerKey, int k) {
        int[][] dp = new int[answerKey.length()][k + 1];
        int num = 0;
        char pre = ' ';
        for (int i = 0; i < answerKey.length(); i++) {
            if (i == answerKey.length() - 1) {
                dp[i][0] = num + 1;
            } else if (answerKey.charAt(i) != pre) {
                dp[i][0] = num + 1;
                pre = answerKey.charAt(i);
                num = 0;
            } else if (answerKey.charAt(i) == pre) {
                num++;
            }
        }
        for (int i = 0; i < answerKey.length(); i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j] = Math.max(dp[i - 1][j - 1] + 1, dp[i - 1][j]);
            }
        }
        return dp[answerKey.length() - 1][k];
    }
}
