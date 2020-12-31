package pri.weiqiang.java.reference;

import java.lang.ref.WeakReference;

public class Weak_Ref1 {

    public static void main(String[] args) throws InterruptedException {
        /* 堆内存没有设置大小，为默认状态 */
        WeakReference<Memory> m = new WeakReference<>(new Memory(1024 * 1024 * 10));
        System.gc(); /* 调用GC */
        Thread.sleep(1000);
    }
}