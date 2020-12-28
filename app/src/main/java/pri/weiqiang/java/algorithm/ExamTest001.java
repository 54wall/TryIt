package pri.weiqiang.java.algorithm;

import java.util.Scanner;

class ExamTest001 {
    public static void main(String[] args) {

//        main1(args);
//        main2(args);
        main3(args);
    }

    public static void main1(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.next();
            System.out.println(Integer.decode(s));
        }
    }

    public static void main2(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String str = in.nextLine();
            System.out.println(Integer.parseInt(str.substring(2), 16));//str.substring(2)表示去除0x
        }
    }


    public static void main3(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String s = in.next();
            s = s.toUpperCase();
            System.out.println("A:"+(int)"A".charAt(0));
            System.out.println("F:"+(int)"F".charAt(0));
            System.out.println("a:"+(int)"a".charAt(0));
            System.out.println("f:"+(int)"f".charAt(0));
            System.out.println("9:"+(int)"9".charAt(0));
            System.out.println("0:"+(int)"0".charAt(0));
            int sum = 0;
            for (int k = 2; k < s.length(); k++) {
                if ((int) (s.charAt(k)) >= 48 && (int) (s.charAt(k)) <= 57) {
                    sum += ((int) s.charAt(k) - 48) * Math.pow(16, s.length() - k - 1);
                } else {
                    int n = (int) s.charAt(k) - 55;
                    sum += n * Math.pow(16, s.length() - k - 1);
                }
            }
            System.out.println(sum);
        }
        in.close();
    }

}
