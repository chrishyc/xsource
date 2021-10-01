package jvm.mine;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public String destCity(List<List<String>> paths) {
        Map<String, List<String>> out = new HashMap<>();
        for (List<String> i : paths) {
            if (!out.containsKey(i.get(0))) out.put(i.get(0), new ArrayList<>());
            out.get(i.get(0)).add(i.get(1));
            if (!out.containsKey(i.get(1))) out.put(i.get(1), new ArrayList<>());
        }
        StringBuilder ans = new StringBuilder();
        out.forEach((k, v) -> {
            if (v.size() == 0) ans.append(k);
        });
        return ans.toString();
    }
}
