/**
 * 一个对象是否有虚引用的存在，完全不会对其生存时间构成影响，
 * 也无法通过虚引用来获取一个对象的实例。
 * 为一个对象设置虚引用关联的唯一目的就是能在这个对象被收集器回收时收到一个系统通知。
 * 虚引用和弱引用对关联对象的回收都不会产生影响，如果只有虚引用活着弱引用关联着对象，
 * 那么这个对象就会被回收。它们的不同之处在于弱引用的get方法，虚引用的get方法始终返回null,
 * 弱引用可以使用ReferenceQueue,虚引用必须配合ReferenceQueue使用。
 * <p>
 * jdk中直接内存的回收就用到虚引用，由于jvm自动内存管理的范围是堆内存，
 * 而直接内存是在堆内存之外（其实是内存映射文件，自行去理解虚拟内存空间的相关概念），
 * 所以直接内存的分配和回收都是有Unsafe类去操作，java在申请一块直接内存之后，
 * 会在堆内存分配一个对象保存这个堆外内存的引用，
 * 这个对象被垃圾收集器管理，一旦这个对象被回收，
 * 相应的用户线程会收到通知并对直接内存进行清理工作。
 * <p>
 * 事实上，虚引用有一个很重要的用途就是用来做堆外内存的释放，
 * DirectByteBuffer就是通过虚引用来实现堆外内存的释放的。
 */


package concurrent;

import sun.misc.Cleaner;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.nio.ByteBuffer;

/**
 * -Xmx20m
 */
public class T22_PhantomReference {
    private static final ReferenceQueue<ByteBuffer> QUEUE = new ReferenceQueue<>();
    
    /**
     * "Finalizer" #3 daemon prio=8 os_prio=31 tid=0x00007fc6fb015000 nid=0x2d03 in Object.wait() [0x0000700007744000]
     *    java.lang.Thread.State: WAITING (on object monitor)
     * 	at java.lang.Object.wait(Native Method)
     * 	- waiting on <0x00000007becbaf78> (a java.lang.ref.ReferenceQueue$Lock)
     * 	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
     * 	- locked <0x00000007becbaf78> (a java.lang.ref.ReferenceQueue$Lock)
     * 	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
     * 	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)
     *
     * "Reference Handler" #2 daemon prio=10 os_prio=31 tid=0x00007fc6fa80d800 nid=0x5203 in Object.wait() [0x0000700007641000]
     *    java.lang.Thread.State: WAITING (on object monitor)
     * 	at java.lang.Object.wait(Native Method)
     * 	- waiting on <0x00000007becc2988> (a java.lang.ref.Reference$Lock)
     * 	at java.lang.Object.wait(Object.java:502)
     * 	at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
     * 	- locked <0x00000007becc2988> (a java.lang.ref.Reference$Lock)
     * 	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
     * @param args
     */
    public static void main(String[] args) {
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        Cleaner.create(buffer, () -> {
            //虚拟机执行
            System.out.println("--- 回收直接内存 ---- " );
        });
        PhantomReference<ByteBuffer> phantomReference = new PhantomReference<>(buffer, QUEUE);
        
        buffer = null;
        System.gc();
        new Thread(() -> {
            while (true) {
                System.out.println(phantomReference.get());
            }
        }).start();
        
        new Thread(() -> {
            while (true) {
                Reference<? extends ByteBuffer> poll = QUEUE.poll();
                if (poll != null) {
                    System.out.println("--- 虚引用对象被jvm回收了 ---- " + poll.getClass().getGenericInterfaces());
                }
            }
        }).start();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}

