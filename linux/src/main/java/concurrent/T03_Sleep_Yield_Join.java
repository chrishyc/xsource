package concurrent;

public class T03_Sleep_Yield_Join {
    public static void main(String[] args) {
//        testSleep();
//        testYield();
        testJoin();
    }

    static void testSleep() {
        new Thread(()->{
            for(int i=0; i<100; i++) {
                System.out.println("A" + i);
                try {
                    Thread.sleep(500);
                    //TimeUnit.Milliseconds.sleep(500)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static void testYield() {
        new Thread(()->{
            for(int i=0; i<100; i++) {
                System.out.println("A" + i);
                if(i%10 == 0) Thread.yield();


            }
        }).start();

        new Thread(()->{
            for(int i=0; i<100; i++) {
                System.out.println("------------B" + i);
                if(i%10 == 0) Thread.yield();
            }
        }).start();
    }

    /**
     * "Thread-1" #11 prio=5 os_prio=31 tid=0x00007fb3939c3800 nid=0x4703 in Object.wait() [0x000070000adb2000]
     *    java.lang.Thread.State: WAITING (on object monitor)
     * 	at java.lang.Object.wait(Native Method)
     * 	- waiting on <0x0000000740016d70> (a java.lang.Thread)
     * 	at java.lang.Thread.join(Thread.java:1252)
     * 	- locked <0x0000000740016d70> (a java.lang.Thread)
     * 	at java.lang.Thread.join(Thread.java:1326)
     * 	at concurrent.T03_Sleep_Yield_Join.lambda$testJoin$4(T03_Sleep_Yield_Join.java:58)
     * 	at concurrent.T03_Sleep_Yield_Join$$Lambda$2/721748895.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     *
     * "Thread-0" #10 prio=5 os_prio=31 tid=0x00007fb3939c2800 nid=0x4603 runnable [0x000070000acaf000]
     *    java.lang.Thread.State: RUNNABLE
     * 	at java.io.FileOutputStream.writeBytes(Native Method)
     * 	at java.io.FileOutputStream.write(FileOutputStream.java:326)
     */
    static void testJoin() {
        Thread t1 = new Thread(()->{
            for(int i=0; i<100000000; i++) {
                System.out.println("A" + i);
//                try {
////                    Thread.sleep(500);
//                    //TimeUnit.Milliseconds.sleep(500)
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });

        Thread t2 = new Thread(()->{

            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i=0; i<100; i++) {
                System.out.println("A" + i);
                try {
                    Thread.sleep(500);
                    //TimeUnit.Milliseconds.sleep(500)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
