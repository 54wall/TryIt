package pri.weiqiang.java.synchronize;

public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            System.out.println("instance == null");
            synchronized (Singleton.class) {
                System.out.println("synchronized");
                if (instance == null) {
                    System.out.println("instance = new Singleton()");
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}