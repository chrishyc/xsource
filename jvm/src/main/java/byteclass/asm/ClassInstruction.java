package byteclass.asm;

public class ClassInstruction {
    private int f;

    public void checkAndSetF(int f) {
        if (f >= 0) {
            this.f = f;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static void sleep(long d) {
        try {
            Thread.sleep(d);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
