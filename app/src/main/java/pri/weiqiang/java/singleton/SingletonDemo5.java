package pri.weiqiang.java.singleton;

/**
 * 3.Double CheckLock实现单例：DCL也就是双重锁判断机制（由于JVM底层模型原因，偶尔会出问题，不建议使用）?
 */
public class SingletonDemo5 {
    private volatile static SingletonDemo5 SingletonDemo5;

    private SingletonDemo5() {
    }

    public static SingletonDemo5 newInstance() {
        if (SingletonDemo5 == null) {
            synchronized (SingletonDemo5.class) {
                if (SingletonDemo5 == null) {
                    SingletonDemo5 = new SingletonDemo5();
                }
            }
        }
        return SingletonDemo5;
    }
}
