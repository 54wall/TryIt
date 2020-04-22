package pri.weiqiang.tryit.thread;

//https://blog.csdn.net/zbw18297786698/article/details/53432879/
public class ThreadStopSafeInterrupted {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    // 使用中断机制，来终止线程
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted ...");
                        break;
                    }

                    try {
                        Thread.sleep(1000);
                        System.out.println("线程运行中 未中断 ...");
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted When Sleep ...");
                        // Thread.sleep()方法由于中断抛出异常。
                        // Java虚拟机会先将该线程的中断标识位清除，然后抛出InterruptedException，
                        // 因为在发生InterruptedException异常的时候，会清除中断标记
                        // 如果不加处理，那么下一次循环开始的时候，就无法捕获这个异常。
                        // 故在异常处理中，再次设置中断标记位
                        Thread.currentThread().interrupt();
                    }

                }
            }
        };

        // 开启线程
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();

    }

}

