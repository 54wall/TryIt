package pri.weiqiang.tryit.lib.mathtest;

import java.util.Scanner;

public class PrimeSum {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        while (true) {
            System.out.println("请输入一个大于2的偶数：");
            Scanner in = new Scanner(System.in);
            int inNum = in.nextInt();
//            int inNum = 4;
            for (int i = 2; i < inNum; i++) {
                int j = inNum - i;
                if (isPrime(i) && isPrime(j)) {
                    System.out.println("偶数：" + inNum + ",可以表示为两个素数的和：" + inNum + "=" + i + "+" + j);
                    break;
                }
            }
        }

    }

    //判断是不是素数
    public static boolean isPrime(int a) {

        boolean flag = true;

        if (a < 2) {// 素数不小于2
            return false;
        } else {
            System.out.println("a:"+a+",Math.sqrt(a):"+Math.sqrt(a));
            for (int i = 2; i <= Math.sqrt(a); i++) {

                if (a % i == 0) {// 若能被整除，则说明不是素数，返回false

                    flag = false;
                    break;// 跳出循环
                }
            }
        }
        return flag;
    }


}
