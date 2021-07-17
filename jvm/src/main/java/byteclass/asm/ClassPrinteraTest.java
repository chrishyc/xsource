package byteclass.asm;

import org.objectweb.asm.ClassReader;

import java.io.IOException;

public class ClassPrinteraTest {
    public static void main(String[] args) throws IOException {
        ClassPrinter cp = new ClassPrinter();
        RemoveMethodAdapter remove = new RemoveMethodAdapter(cp, "floatValue", "()F");
        ClassReader cr = new ClassReader("java.lang.Integer");
        cr.accept(remove, 0);
    }
}
