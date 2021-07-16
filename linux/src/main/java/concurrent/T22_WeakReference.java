/**
 * 弱引用遭到gc就会回收
 *
 */
package concurrent;

import java.lang.ref.WeakReference;

public class T22_WeakReference {
    public static void main(String[] args) {
        WeakReference<T22_NormalReference.M> m = new WeakReference<>(new T22_NormalReference.M());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());


        ThreadLocal<T22_NormalReference.M> tl = new ThreadLocal<>();
        tl.set(new T22_NormalReference.M());
        tl.remove();

    }
}

