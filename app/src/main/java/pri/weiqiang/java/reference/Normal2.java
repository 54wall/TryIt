package pri.weiqiang.java.reference;

/**
 * https://blog.csdn.net/a159357445566/article/details/108588947
 * 详解Java的四种引用——强软弱虚
 */
public class Normal2 {

    public static void main(String[] args) throws InterruptedException {

        /* 我们设定JVM参数，设置堆内存大小为25MB */


        /* 栈变量m1对new出来的Memory对象的引用为强引用 */

        /* 申请了20MB的内存，实际会大于20MB，因为我们的byte[]被Memory对象wrapper */
        Memory m1 = new Memory(1024 * 1024 * 20);

        System.gc();
        Thread.sleep(1000);

        /* 再申请10MB堆内存 */
        Memory m2 = new Memory(1024 * 1024 * 10);
    }

}
