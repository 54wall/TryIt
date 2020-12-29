package pri.weiqiang.java.singleton;

/**
 * 如何选用：
 * -单例对象 占用资源少，不需要延时加载，枚举 好于 饿汉
 * -单例对象 占用资源多，需要延时加载，静态内部类 好于 懒汉式
 */
public class UseSingleton {

    public static void main(String[] args) {
        Singleton.single.sayHello();
    }


}
