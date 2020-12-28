package pri.weiqiang.java.algorithm;

import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

public class ExamTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {

            TreeSet<Integer> set = new TreeSet<Integer>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return 0;
                }
            });
            int n = sc.nextInt();
            System.out.println("n:"+n);
//            if (n > 0) {
                for (int i = 0; i < n; i++) {
                    System.out.println("sc.nextInt():"+sc.nextInt());
                    set.add(sc.nextInt());
                }
//            }
            for (Integer i : set) {
                System.out.println("i:"+i);
            }
        }
//        test1();
    }


    public static void test1() {

    }

    public static void main2(String[] args) {
        //定义一个3行二维数组
        int[][] nums=new int[3][];
        //给每一行分配不同的列
        for(int i=0;i<nums.length;i++)
        {

            nums[i]=new int[i+2];
        }
        //给nums[][]赋值
        System.out.println("数组元素的值及排列方式如下：");
        int a=10;
        for(int i=0;i<nums.length;i++)
        {

            for(int j=0;j<nums[i].length;j++)
            {
                nums[i][j]=a;
                System.out.print(" "+nums[i][j]+" ");
                a--;
            }
            System.out.println();
        }
        //计算数组中元素的个数
        int b=0;
        for (int i=0;i<nums.length;i++)
        {
            //控制列
            for(int j=0;j<nums[i].length;j++)
            {
                b++;
            }
        }
        System.out.println("数组中元素的个数为："+b);
        //给数组元素从大到小排序
        System.out.print("数组元素从大到小排序为：");
        //依次找出剩余元素中的最大值
        for (int k=1;k<=b;k++)
        {
            //假设最大值是第一行第一个数
            int max=nums[0][0];
            //控制行
            for (int i=0;i<nums.length;i++)
            {
                //控制列
                for(int j=1;j<nums[i].length;j++)
                {
                    if(max<nums[i][j])
                    {
                        /*
                         * 当max小于nums[i][j]中的元素时
                         * 将max的值与nums[i][j]中的元素对调
                         */
                        int n= max;
                        max=nums[i][j];
                        nums[i][j]=n;
                    }
                }
            }
            /*
             *因为第一次循环已经结束；n[0][0]的值要么是最大值被输出了，要么已经被赋给n[i][j]了，
             *所以让n[0][0]的值为0；可以让前一次循环输出的最大值不再出现在下一次循环当中
             */
            nums[0][0]=0;
            System.out.print(" "+max+" ");
        }
    }

}
