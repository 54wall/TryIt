package pri.weiqiang.java.synchronize;

public class VolatileDemo {
    private static /*volatile*/ int lower = 0;
    private static /*volatile*/ int upper = 10;

    private static int getLower() {
        return lower;
    }

    private static void setLower(int value) {
        if (value > upper) {
            throw new IllegalArgumentException("不能设置区间使最小值大于最大值");
        }
        lower = value;
        System.out.println("setLower 设置完成" + "[" + getLower() + "," + getUpper() + "]");
    }

    private static int getUpper() {
        return upper;
    }

    private static void setUpper(int value) {
        if (value < lower) {
            throw new IllegalArgumentException("不能设置区间使最大值小于最小值");
        }
        upper = value;
        System.out.println("setUpper 设置完成" + "[" + getLower() + "," + getUpper() + "]");
    }

    public static void main(String[] args) {


        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    setUpper(5);

                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    setLower(8);
                }
            }).start();
        }

    }

}
