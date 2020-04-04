package asm;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;

import static org.objectweb.asm.Opcodes.*;

public class ClassWriterTest {
    public static void main(String[] args) {
        ClassWriter cw = new ClassWriter(0);
        TraceClassVisitor tcv = new TraceClassVisitor(cw, new PrintWriter(System.out));
        CheckClassAdapter cv = new CheckClassAdapter(tcv);
        AddTimerAdapter addTimerAdapter = new AddTimerAdapter(cv);
        addTimerAdapter.visit(V1_5, ACC_PUBLIC + ACC_INTERFACE,
                "Comparable", null, "java/lang/Object",
                null);
        addTimerAdapter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        addTimerAdapter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        addTimerAdapter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        addTimerAdapter.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        addTimerAdapter.visitEnd();
        byte[] b = cw.toByteArray();
    }
}
