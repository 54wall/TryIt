package pri.weiqiang.java.synchronize;

public class VolatileDemo1 {

    public static void main(String[] args) {


        for (int i = 0; i < 500; i++) {
//            System.out.println("i:"+i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Singleton.getInstance();

                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Singleton.getInstance();
                }
            }).start();
        }

    }


}
