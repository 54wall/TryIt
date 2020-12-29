package pri.weiqiang.java.singleton;

/**
 * .静态内部类实现模式（线程安全，调用效率高，可以延时加载）
 */
public class SingletonDemo3 {

    private static class SingletonClassInstance {
        private static final SingletonDemo3 instance = new SingletonDemo3();
    }

    private SingletonDemo3() {
    }

    public static SingletonDemo3 getInstance() {
        return SingletonClassInstance.instance;
    }

}
