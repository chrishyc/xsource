package byteclass.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class SAMifierTest {
    public static void main(String[] args) {
        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;
        
        cw.visit(52, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "Runnable", null, "java/lang/Object", null);
        
        cw.visitSource("Runnable.java", null);
        
        {
            av0 = cw.visitAnnotation("Ljava/lang/FunctionalInterface;", true);
            av0.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "run", "()V", null, null);
            mv.visitEnd();
        }
        cw.visitEnd();
        MyClassLoader classLoader = new MyClassLoader();
        Class runnable = classLoader.defineClass("Runnable", cw.toByteArray());
        
    }
    
}
