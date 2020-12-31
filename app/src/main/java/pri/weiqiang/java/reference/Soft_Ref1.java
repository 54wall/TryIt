package pri.weiqiang.java.reference;

import java.lang.ref.SoftReference;

public class Soft_Ref1 {

    public static void main(String[] args) throws InterruptedException {
        /* 堆内存大小为50MB */
        /* 申请30MB */
        SoftReference<Memory> m1 = new SoftReference<>(new Memory(1024 * 1024 * 30));

        System.gc(); /* 显示调用GC */

        /* 此时内存够用，所以结果可以预见性的为GC不会回收被软引用指向的对象 */

        Thread.sleep(1000);
    }
}
