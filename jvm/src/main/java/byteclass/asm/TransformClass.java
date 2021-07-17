package byteclass.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class TransformClass {
    public static void main(String[] args) {
        byte[] b1 = new byte[10];
        ClassWriter cw = new ClassWriter(0);
        ClassVisitor cv = new ChangeVersionAdapter(cw);
        ClassReader cr = new ClassReader(b1);
        cr.accept(cv, 0);
        byte[] b2 = cw.toByteArray();
    }
}
