package jvm.mine;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * 属性表
 * Signature,记录泛型签名信息<String>
 *
 * Signature: #108
 */
@Slf4j
public abstract class T_02_class_structure_attr_03_method extends Thread implements Callable<String> {
    
    private boolean flag = true;
    private byte b = 1;
    private char c = 'a';
    private short s = 2;
    private int i = 3;
    private float f = 3.4f;
    private long l = 4;
    private double d = 5.1d;
    private static String s_var = "hello static";
    private final String f_var = "hello final";
    
    @Value("1")
    private String anno_var;
    
    {
        int construct_func = 1;
    }
    
    static {
        int static_construct_func = 1;
    }
    
    public T_02_class_structure_attr_03_method() {
    
    }
    
    /**
     * 属性表:
     * Code
     *
     * Code:
     *       stack=3, locals=1, args_size=1
     *          0: aload_0
     *          1: dup
     *          2: getfield      #3                  // Field b:B
     *          5: iconst_1
     *          6: iadd
     *          7: i2b
     *          8: putfield      #3                  // Field b:B
     *         11: return
     *       LineNumberTable:
     *         line 52: 0
     *         line 53: 11
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0      12     0  this   Ljvm/mine/T_02_class_structure;
     */
    public void code() {
        b++;
    }
    
    /**
     * 属性表:
     * code,行号表
     */
    public void lineNumberTable() {
        i++;
    }
    
    /**
     * 属性表:
     * code,关联局部变量名称
     */
    public void localVariableTable() {
        l++;
    }
    
    /**
     * 属性表:
     * code,使用字段的特征签名来完 成泛型的描述
     *
     * public void localVariableTypeTable();
     *     descriptor: ()V
     *     flags: ACC_PUBLIC
     *     Code:
     *       stack=2, locals=2, args_size=1
     *          0: new           #17                 // class java/util/ArrayList
     *          3: dup
     *          4: invokespecial #18                 // Method java/util/ArrayList."<init>":()V
     *          7: astore_1
     *          8: return
     *       LineNumberTable:
     *         line 76: 0
     *         line 77: 8
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0       9     0  this   Ljvm/mine/T_02_class_structure;
     *             8       1     1  list   Ljava/util/List;
     *       LocalVariableTypeTable:
     *         Start  Length  Slot  Name   Signature
     *             8       1     1  list   Ljava/util/List<Ljava/lang/Thread;>;
     */
    public void localVariableTypeTable() {
        List<Thread> list = new ArrayList<>();
    }
    
    /**
     * 属性表:
     * code,异常表
     *
     * Exception table:
     *          from    to  target type
     *              0    11    24   Class java/lang/NullPointerException
     *              0    11    49   any
     *             24    36    49   any
     */
    public void exceptionTable() {
        try {
            b++;
        } catch (NullPointerException e) {
            c++;
        } finally {
            l++;
        }
    }
    
    /**
     * 属性表:
     * code,stackMapTable,字节码校验
     *
     * StackMapTable: number_of_entries = 2
     *         frame_type = 22
     *         frame_type =18
     *
     */
    public void stackMapTable() {
        if (b == 2) {
            b++;
        } else if (c == 3) {
            c--;
        }
    }
    
    /**
     * 属性表:
     * 方法参数描述
     *
     *
     */
    abstract void methodParameters(List<Integer> list, String name);
    
    
    /**
     * 属性表:
     * 运行时注解
     *
     *public void runtimeVisibleAnnotations();
     *     descriptor: ()V
     *     flags: ACC_PUBLIC
     *     Code:
     *       stack=0, locals=1, args_size=1
     *          0: return
     *       LineNumberTable:
     *         line 168: 0
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0       1     0  this   Ljvm/mine/T_02_class_structure;
     *     RuntimeVisibleAnnotations:
     *       0: #99()
     *
     */
    @Test
    public void runtimeVisibleAnnotations() {
    
    }
    
    /**
     * 属性表:
     * 运行时参数注解
     *
     * public void runtimeVisibleParameterAnnotations(java.lang.String);
     *     descriptor: (Ljava/lang/String;)V
     *     flags: ACC_PUBLIC
     *     Code:
     *       stack=0, locals=2, args_size=2
     *          0: return
     *       LineNumberTable:
     *         line 176: 0
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0       1     0  this   Ljvm/mine/T_02_class_structure;
     *             0       1     1     s   Ljava/lang/String;
     *     RuntimeVisibleTypeAnnotations:
     *       0: #103(): METHOD_FORMAL_PARAMETER, param_index=0
     *     RuntimeVisibleParameterAnnotations:
     *       parameter 0:
     *         0: #103()
     */
    public void runtimeVisibleParameterAnnotations(@NotEmpty String s) {
    
    }
    
    /**
     * 属性表:
     * 运行时类型注解
     */
    public void runtimeVisibleTypeAnnotations(List<NotEmpty> list) {
    
    }
    
    
    /**
     * 属性表:
     * 异常属性,Exceptions
     * 列举出方法中可能抛出的受查异常
     *
     * public void exceptions() throws java.lang.NullPointerException, java.io.IOException;
     *     descriptor: ()V
     *     flags: ACC_PUBLIC
     *     Code:
     *       stack=2, locals=1, args_size=1
     *          0: new           #19                 // class java/lang/NullPointerException
     *          3: dup
     *          4: invokespecial #20                 // Method java/lang/NullPointerException."<init>":()V
     *          7: athrow
     *       LineNumberTable:
     *         line 193: 0
     *       LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             0       8     0  this   Ljvm/mine/T_02_class_structure;
     *     Exceptions:
     *       throws java.lang.NullPointerException, java.io.IOException
     */
    public void exceptions() throws NullPointerException, IOException {
        throw new NullPointerException();
    }
    
    /**
     * 属性表:
     * 常量属性
     *
     * public static final int constantValue;
     *     descriptor: I
     *     flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL
     *     ConstantValue: int 1
     */
    public static final int constantValue = 1;
    
    /**
     * 属性表:
     * InnerClasses属性
     * 每一个内部类的信息都由一个 inner_classes_info表进行描述
     *
     * InnerClasses:
     *      private #41= #40 of #33; //inner=class jvm/mine/T_02_class_structure$inner of class jvm/mine/T_02_class_structure
     *      static #21; //class jvm/mine/T_02_class_structure$1
     */
    private class inner {
        private int inner_i = 1;
    }
    
    
    public static void main(String[] args) {
        T_02_class_structure_attr_03_method o = new T_02_class_structure_attr_03_method() {
            @Override
            void methodParameters(List<Integer> list, String name) {
        
            }
        };
        o.anno_var = "2";
        o.i = new Random().nextInt();
        if (o.i % 2 == 1) {
            throw new RuntimeException("fund you");
        } else {
            o.call();
        }
    }
    
    @Override
    public String call() {
        return "interface";
    }
    
    @Test
    public void constantPool(){
        String.valueOf("fdfsadf");
    }
    
}
