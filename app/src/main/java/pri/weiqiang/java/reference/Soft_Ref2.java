package pri.weiqiang.java.reference;

import java.lang.ref.SoftReference;

public class Soft_Ref2 {
    public static void main(String[] args) throws InterruptedException {

        /* 堆内存大小为50MB */

        /* 申请30MB */
        SoftReference<Memory> m1 = new SoftReference<>(new Memory(1024 * 1024 * 30));


        /* 申请20MB */
        for (int i = 0; i < 20; ++i) {
            System.out.println("[time] => " + System.currentTimeMillis());
            SoftReference<Memory> ma = new SoftReference<>(new Memory(1024 * 1024));
            Thread.sleep(200);
        }
    }
}