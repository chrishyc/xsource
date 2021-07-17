package jvm;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

public class ClassLoaderOOMOps extends ClassLoader implements Opcodes {
    public static void main(final String args[]) throws Exception {
        new ThreadAndListHolder(); // ThreadAndListHolder 类中会加载大对象
        
        List<ClassLoader> classLoaders = new ArrayList<ClassLoader>();
        final String className = "ClassLoaderOOMExample";
        final byte[] code = geneDynamicClassBytes(className);
        
        // 循环创建自定义 class loader，并加载 ClassLoaderOOMExample
        while (true) {
            ClassLoaderOOMOps loader = new ClassLoaderOOMOps();
            Class<?> exampleClass = loader.defineClass(className, code, 0, code.length); //将二进制流加载到内存中
            classLoaders.add(loader);
             exampleClass.getMethods()[0].invoke(null, new Object[]{null});  // 执行自动加载类的方法，通过反射调用main
        }
    }
    
    private static byte[] geneDynamicClassBytes(String className) throws Exception {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_1, ACC_PUBLIC, className, null, "java/lang/Object", null);
        
        //生成默认构造方法
        MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        
        //生成构造方法的字节码指令
        mw.visitVarInsn(ALOAD, 0);
        mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mw.visitInsn(RETURN);
        mw.visitMaxs(1, 1);
        mw.visitEnd();
        
        //生成main方法
        mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        //生成main方法中的字节码指令
        mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        
        mw.visitLdcInsn("Hello world!");
        mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        mw.visitInsn(RETURN);
        mw.visitMaxs(2, 2);
        mw.visitEnd();  //字节码生成完成
        
        return cw.toByteArray();  // 获取生成的class文件对应的二进制流
        
    }
}
