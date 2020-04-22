package pri.weiqiang.java.thread;

public class ThreadLocalTest {
    static final String CONSTANT_01 = "CONSTANT_01";
    static final String CONSTANT_02 = "CONSTANT_02";

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> threadLocal = new ThreadLocal<String>();
        threadLocal.set(CONSTANT_01);

        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<String>();
        inheritableThreadLocal.set(CONSTANT_01);

        Thread thread_1 = new TestThread(threadLocal, inheritableThreadLocal);
        thread_1.setName("thread_01");
        thread_1.start();

        thread_1.join();

        System.out.println("   " + Thread.currentThread().getName() + "  *****************main()*************************");
        System.out.println("   " + Thread.currentThread().getName() + "   \tThreadLocal: " + threadLocal.get());
        System.out.println("   " + Thread.currentThread().getName() + "   \tInheritableThreadLocal: " + inheritableThreadLocal.get());
        System.out.println("   " + Thread.currentThread().getName() + "  ******************************************");
    }
}

class TestThread extends Thread {
    ThreadLocal<String> threadLocal;
    InheritableThreadLocal<String> inheritableThreadLocal;

    public TestThread(ThreadLocal<String> threadLocal, InheritableThreadLocal<String> inheritableThreadLocal) {
        super();
        this.threadLocal = threadLocal;
        this.inheritableThreadLocal = inheritableThreadLocal;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + "******************** before **********************");
        System.out.println(Thread.currentThread().getName() + "\tThreadLocal: " + threadLocal.get());
        System.out.println(Thread.currentThread().getName() + "\tInheritableThreadLocal: " + inheritableThreadLocal.get());
        System.out.println(Thread.currentThread().getName() + "******************************************\n");

        threadLocal.set(ThreadLocalTest.CONSTANT_02);
        inheritableThreadLocal.set(ThreadLocalTest.CONSTANT_02);

        System.out.println(Thread.currentThread().getName() + "*************(Reset Value)****************");
        System.out.println(Thread.currentThread().getName() + "\tThreadLocal: " + threadLocal.get());
        System.out.println(Thread.currentThread().getName() + "\tInheritableThreadLocal: " + inheritableThreadLocal.get());
        System.out.println(Thread.currentThread().getName() + "*************(Reset Value)****************\n");
    }
}

