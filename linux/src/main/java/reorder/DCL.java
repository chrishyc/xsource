package reorder;

/**
 * javap -l
 */
public class DCL {
    int a = 8;
    /**
     * volatile必须要,防止new的三个指令重排
     *
     */
    volatile static DCL dcl;
    
    public static DCL getInstance() {
        if (dcl == null) {
            synchronized (DCL.class) {
                if (dcl == null) {
                    dcl = new DCL();
                }
            }
        }
        return dcl;
    }
    
    public static void main(String[] args) {
        while (new DCL().a!=0){
        }
        System.err.println("指令重排序了");
    }
}
