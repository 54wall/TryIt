package pri.weiqiang.java.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;

public class Phantom_Ref {
    static final ArrayList<byte[]> LIST = new ArrayList<>();

    static final ReferenceQueue<Memory> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) throws InterruptedException {

        PhantomReference<Memory> m = new PhantomReference<>(new Memory(1024 * 1024 * 10), QUEUE);
        new Thread(() -> {
            while (true) {
                LIST.add(new byte[1024 * 1024]);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(m.get()); /* 虚引用指向的值永远无法被获取 */
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Reference<? extends Memory> poll = QUEUE.poll();
                if (poll != null) {
                    /* 虚引用在对象回收时，会进行通知 */
                    System.out.println("有虚引用被GC回收了-" + poll);
                    break;
                }
            }
        }).start();
    }
}
