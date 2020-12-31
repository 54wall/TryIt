package pri.weiqiang.java.reference;

public class Normal1 {
    public static void main(String[] args) throws InterruptedException {

        /* 栈变量m对new出来的Memory对象的引用为强引用 */
        Memory m = new Memory(1024 * 1024 * 20);

        /* 现在堆中的对象没有引用指向它，它要被GC回收 */
        m = null;

        System.gc(); /* 显式GC */

        /*
         * 当我们启动java程序时，默认会有两个线程，一个是我们的主线程，另一个便是GC线程。
         * 通常GC线程的优先级比较低，并且GC线程默认为守护线程，即它会在主线程退出的同
         * 时退出。
         *
         * 为了观察到GC的效果，我们让主线程休眠1s
         */
        Thread.sleep(1000);
    }

}