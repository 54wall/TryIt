package pri.weiqiang.tryit.lib.mathtest;

import java.util.Scanner;

class IsPrimeNumber {

    public static void main(String[] args) {
//        System.out.println("判断一个数,是否是素数请输入");
//        Scanner scanner = new Scanner(System.in);
//        int input = scanner.nextInt();
       int input = 5;
       boolean isPrime =  isPrime(input);
        System.out.println("result isPrime:" + isPrime);

    }

    public static boolean isPrime(int input){
       boolean isPrime = false;
       if (input <= 2) {
          isPrime = true;
       } else {
          for (int i = 2; i < input - 1; i++) {
             if (input % i == 0) {
                isPrime = false;
                System.out.println("是否是素数:"+isPrime);
                return isPrime;
             }
          }
       }

       System.out.println("last 是否是素数:"+isPrime);
       isPrime = true;
       return isPrime;
    }
}
