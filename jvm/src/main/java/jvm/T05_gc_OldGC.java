package jvm;

/**
 * 思考题：什么时候触发YGC？
 */
//-Xmn10M = -XX:NewSize=10M -XX:MaxNewSize=10M -XX:NewRatio -Xlog:c5_gc*
public class T05_gc_OldGC {
    public static void main(String[] args) {
        byte[] b1 = new byte[4 * 1024 * 1024];
        //b1 = null;
        System.out.println("1----------------------");
        byte[] b2 = new byte[4 * 1024 * 1024];
    }
}
