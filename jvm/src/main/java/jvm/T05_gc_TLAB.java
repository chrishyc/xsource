package jvm;

//-XX:-DoEscapeAnalysis -XX:-EliminateAllocations -XX:-UseTLAB -Xlog:c5_gc*
// 逃逸分析 标量替换 线程专有对象分配
public class T05_gc_TLAB {
    //User u;
    class User {
        int id;
        String name;
        
        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    
    void alloc(int i) {
        new User(i, "name " + i);
    }
    
    public static void main(String[] args) {
        T05_gc_TLAB t = new T05_gc_TLAB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000_0000; i++) t.alloc(i);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        
        //for(;;);
    }
}
