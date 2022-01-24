package jvm;

import java.util.ArrayList;
import java.util.List;

public class T03_runtime_instruction_invokeMethod {
    public static void main(String[] args) {
        T03_runtime_instruction_invokeMethod special = new T03_runtime_instruction_invokeMethod();
        special.invokeVirtual();
        invokeStatic();

        List<String> list = new ArrayList<>();
        list.add("hello");

        // InvokeDynamic
        I i = C::n;
        I i2 = C::n;
        I i3 = C::n;
        I i4 = () -> {
            C.n();
        };
        System.out.println(i.getClass());
        System.out.println(i2.getClass());
        System.out.println(i3.getClass());

        //for(;;) {I j = C::n;} //MethodArea <1.8 Perm Space (FGC不回收)
    }

    public static void invokeStatic() {
    }

    public void invokeVirtual() {
    }

    @FunctionalInterface
    public interface I {
        void m();
    }

    public static class C {
        static void n() {
            System.out.println("hello");
        }
    }
}
