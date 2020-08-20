package infrastructure;

public class Recursive {
    private static ThreadLocal<Integer> retryTimes = ThreadLocal.withInitial(() -> 0);
    
    public static void recur(String hello) {
        retryTimes.set(retryTimes.get() + 1);
        try {
            System.out.println("开始执行,第" + retryTimes.get() + "次");
            if (hello.equalsIgnoreCase("error")) {
                throw new RuntimeException();
            }
            System.out.println("正常结束");
            retryTimes.set(0);
        } catch (Exception e) {
            if (hello.equalsIgnoreCase("error") && retryTimes.get() < 3) {
                System.out.println("异常后,递归" + retryTimes.get() + "次");
                recur(hello);
            } else {
                retryTimes.set(0);
                System.out.println("异常次数归零");
            }
        } finally {
            System.out.println("执行finally");
        }
        
    }
    
    public static void main(String[] args) {
        recur("error");
    }
}
