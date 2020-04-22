package pri.weiqiang.java.algorithm;

import java.util.ArrayList;

public class LittleTest {

    public static void main(String[] args) {
//        q1();
//        q2();
//        q3();
//        q4();
        q5();
    }

    //1.求下列代码执行后result的值
    private static void q1() {
        int result = ((0xF0FF & 0x00F0) << 1) / (4 >> 1);
        System.out.println("result:" + result);
    }

    private static String func(String s) {
        return s.length() > 0 ? (func(s.substring(1)) + s.charAt(0)) : "";
//        return s.length() > 0 ? func(s.substring(1)) + s.charAt(0) : "";
    }

    private static void q2() {
        String result = func("Tencent");
        System.out.println("result:" + result);
    }

    private static void q3() {
        Integer a = new Integer(1234);
        Integer b = new Integer(1234);
        boolean result = a == b;
        System.out.println("result:" + result);
    }

    //不用库函数，用java语言实现，将一整型数字转化为字符串数组。
    public static void q4() {
        int n = 666;
        char[] a = new char[10];
        int i = 0;
        while (n > 0) {
            a[i] = (char) (n % 10 + '0');
            i++;
            n = n / 10;
        }
        for (char s : a
        ) {
            System.out.println("s:" + s);
        }
    }
    //3.输入一个非完全二叉树和根节点，广度遍历它的所有根节点，并分层打印所有节点
    //节点类
//    Node{
//        String value;
//        Node left;
//        Node right;
//    }

    /**
     * @author HRX
     * @version 创建时间：2018年9月28日 下午8:30:26
     * 类说明
     * https://blog.csdn.net/qq_39429962/article/details/82908166
     */
    public static void q5() {
        int[] num = {-9, -1, 5, -3, 7, -9, 3, -1, -1, -2, 4, 2, 2, 2, -1, -5};
        //	int[] num = new int[6];
        int sum = num[0];    //最大子集的和
        int temp = 0;    //临时数用于比较
        //产生随机数组用于测试
//        for (int i = 0; i < num.length; i += 2) {
//            num[i] = (int) (Math.random() * 10);
//            num[i + 1] = (int) (-Math.random() * 10);
//        }
        //查看数组有的
        for (int x : num)
            System.out.println(x);
        for (int x : num) {
            if (temp <= 0) {    //当前边自己之和小于等于0时，temp跳到当前位置，sum仍然记录最大值
                temp = temp > 0 ? temp + x : x;    //temp为负就舍去它，取后边的数
                sum = sum > x ? sum : x;
            } else {
                temp += x;
                sum = temp > sum ? temp : sum;
            }
            System.out.println(temp + "-----------------" + sum);  //测试temp跟sum的每一次过程
        }
        System.out.println("最大子集值:" + sum);
    }

    //2.写出一个函数,输入两个字符串，输出这两个字符串的字符的交集，例如s1="aie",s2="validate" 交集就是"aie"
    //String intersect(String s1,String s2)
    public String intersect(String s1, String s2) {
        char[] s1_char = s1.toCharArray();
        char[] s2_char = s2.toCharArray();

        ArrayList<CharSequence> list = new ArrayList<CharSequence>();
        for (int i = 0; i <= s1_char.length; i++) {
            for (int j = 0; j <= s2_char.length; j++) {
                if (s1_char[i] == s2_char[j]) {
//                    s1.contains(s1_char[j]);
                }
            }
        }
        return "Not finish";

    }


}
