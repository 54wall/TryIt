package pri.weiqiang.tryit.lib.chartest;

class Reverse {

    public static void main(String[] args) {
        System.out.println("This is Reverse");
        int x = 1534236469;
        System.out.println("reverse last:" + reverse(x));
    }

    public static int reverse(int x) {
        System.out.println("input x:" + x);

        int result = 0;
        //包含负数所以是x / 10 != 0 如果是正数，则写x /10 >0
        while (x / 10 != 0) {
            int temp = result * 10 + x % 10;
//            if (temp / 10 != result) {
//                return 0;
//            }
            result = temp;
            x = x / 10;
        }
        result = result*10+x;
        System.out.println("result x:" + result);
        return result;
    }


    public static int reverse2(int x) {
        System.out.println("input x:" + x);

        int result = 0;
        while (x != 0) {
            int temp = result * 10 + x % 10;
            if (temp / 10 != result) {
                return 0;
            }
            result = temp;
            x  /= 10;
        }

        System.out.println("result x:" + x);
        return result;
    }
}
