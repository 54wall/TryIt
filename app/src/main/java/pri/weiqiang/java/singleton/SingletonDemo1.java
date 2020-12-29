package pri.weiqiang.java.singleton;

/**
 * https://www.cnblogs.com/ngy0217/p/9006716.html
 * 饿汉式(线程安全，调用效率高，但是不能延时加载)：
 * 一上来就把单例对象创建出来了，要用的时候直接返回即可，这种可以说是单例模式中最简单的一种实现方式。
 * 但是问题也比较明显。单例在还没有使用到的时候，初始化就已经完成了。也就是说，如果程序从头到位都没用使用这个单例的话，
 * 单例的对象还是会创建。这就造成了不必要的资源浪费。所以不推荐这种实现方式。
 */
public class SingletonDemo1 {
    private static final SingletonDemo1 instance = new SingletonDemo1();

    private SingletonDemo1() {
    }

    public static SingletonDemo1 getInstance() {
        return instance;
    }
}
