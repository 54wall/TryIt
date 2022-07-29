package pri.weiqiang.tryit.lib.mathtest;

import java.util.Scanner;

/**
 * 任何一个自然数都能分解成两个素数(素数也叫质数)之和
 */
class PrimeSumTest {
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();
            primeSum(number);
        }
    }

    public static void primeSum(int number) {
        for (int i=1;i<number;i++){
            int j = number - i;
            if(isPrime(i)&&isPrime(j)){
                System.out.println("i:"+i+",j:"+j);
                break;
            }
        }
    }

    public static boolean isPrime(int number) {
        if (number <= 2) {
            return true;
        } else {
            for (int i = 2; i < number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
