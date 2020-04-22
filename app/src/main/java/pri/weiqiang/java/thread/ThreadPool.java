package pri.weiqiang.java.thread;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * https://www.jianshu.com/p/7b2da1d94b42*/
public class ThreadPool {
    public static void main(String[] args) {
        int i = 1;
        switch (i) {
            case 1:
                startThreadPoolExecutor();
                break;
            case 2:
                startFixedThreadPool();
                break;
            case 3:
                startCachedThreadPool();
                break;
            case 4:
                startSingleThreadPool();
                break;
            case 5:
                startScheduledThreadPool();
                break;
            case 6:
                startPriorityThreadPool();
                break;
        }
    }


    /*corePoolSize: 该线程池中核心线程的数量。
    maximumPoolSize：该线程池中最大线程数量。(区别于corePoolSize)
    keepAliveTime:从字面上就可以理解，是非核心线程空闲时要等待下一个任务到来的时间，当任务很多，每个任务执行时间很短的情况下调大该值有助于提高线程利用率。注意：当allowCoreThreadTimeOut属性设为true时，该属性也可用于核心线程。
    unit:上面时间属性的单位
    workQueue:任务队列，后面详述。
    threadFactory:线程工厂，可用于设置线程名字等等，一般无须设置该参数。
    设置好几个参数就可以创建一个基本的线程池，而之后的各种线程池都是在这种基本线程池的基础上延伸的。*/
    private static void startThreadPoolExecutor() {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100));
        for (int i = 0; i < 30; i++) {
            final int finali = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        System.out.println("startThreadPoolExecutor run: " + finali);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            //结果会每2s打印三个日志。
            threadPoolExecutor.execute(runnable);
        }
    }

    //特点：参数为核心线程数，只有核心线程，无非核心线程，并且阻塞队列无界。
    private static void startFixedThreadPool() {
        //创建fixed线程池
        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 30; i++) {
            final int finali = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        System.out.println("startFixedThreadPool run: " + finali);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            //结果为每5s打印一次任务，跟上面的基础线程池类似。
            fixedThreadPool.execute(runnable);

        }
    }

    private static void startCachedThreadPool() {
        //创建Cached线程池
        final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 30; i++) {
            final int finali = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        System.out.println("startCachedThreadPool run: " + finali);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            //结果：过2s后直接打印30个任务
            cachedThreadPool.execute(runnable);

        }
    }

    private static void startSingleThreadPool() {
        //创建Single线程池
        final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 30; i++) {
            final int finali = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        System.out.println("startSingleThreadPool run: " + finali);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            //结果：每2s打印一个任务，由于只有一个核心线程，当被占用时，其他的任务需要进入队列等待。
            singleThreadExecutor.execute(runnable);

        }
    }

    private static void startScheduledThreadPool() {
        //创建Scheduled线程池
        final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("startScheduledThreadPool run: " + "This task is delayed to execute");
            }

        };

        scheduledThreadPool.schedule(runnable, 10, TimeUnit.SECONDS);//延迟启动任务
        //延迟5s后启动，每1s执行一次
        // scheduledThreadPool.scheduleAtFixedRate(runnable,5,1,TimeUnit.SECONDS);
        //启动后第一次延迟5s执行，后面延迟1s执行
        // scheduledThreadPool.scheduleWithFixedDelay(runnable,5,1,TimeUnit.SECONDS);
    }

    //    结果：前三个任务被创建的三个核心线程执行，之后的27个任务进入队列并且调用compareTo方法进行排序，之后打印出来的是经过排序后从大到小的顺序。
    private static void startPriorityThreadPool() {
        //创建自定义线程池(优先级线程)
        final ExecutorService priorityThreadPool = new ThreadPoolExecutor(3, 3, 0,
                TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        for (int i = 0; i < 30; i++) {
            final int priority = i;
            priorityThreadPool.execute(new PriorityRunnable(priority) {
                @Override
                protected void doSomeThing() {
                    System.out.println("优先级为 " + priority + "  的任务被执行");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void demo() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
            }
        });
        thread.start();

    }

    public static abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {
        private int priority;

        PriorityRunnable(int priority) {
            if (priority < 0) {
                throw new IllegalArgumentException();
            }
            this.priority = priority;
        }

        int getPriority() {
            return priority;
        }

        @Override
        public int compareTo(@NonNull PriorityRunnable another) {
            int me = this.priority;
            int anotherPri = another.getPriority();
            return me == anotherPri ? 0 : me < anotherPri ? 1 : -1;
        }

        @Override
        public void run() {
            doSomeThing();
        }

        protected abstract void doSomeThing();
    }
}
