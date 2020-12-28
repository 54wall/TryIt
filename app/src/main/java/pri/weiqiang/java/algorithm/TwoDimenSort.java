package pri.weiqiang.java.algorithm;


import java.util.Arrays;

public class TwoDimenSort {
    public static void main(String[] args) {
        int xLen = 5;
        int yLen = 8;
        int[][] a = new int[xLen][yLen];
        //使用随机数填充二维数组
        for(int i=0;i<xLen;i++) {
            for(int j=0;j<yLen;j++) {
                a[i][j] = (int)(Math.random() * 100);
            }
        }
        //复制二维数组到一维数组
        int[] b = new int[xLen * yLen];
        int k = 0;
        for(int i=0;i<xLen;i++) {
            for(int j=0;j<yLen;j++) {
                b[k++] = a[i][j];
            }
        }
        System.out.println("排序前:");
        for(int i=0;i<xLen;i++) {
            System.out.println(Arrays.toString(a[i]));
        }
        //调用Arrays中的sort方法
        Arrays.sort(b);
        //将排序后的值复制回二维数组
        k = 0;
        for(int i=0;i<xLen;i++) {
            for(int j=0;j<yLen;j++) {
                a[i][j] = b[k++];
            }
        }
        System.out.println("排序后:");
        for(int i=0;i<xLen;i++) {
            System.out.println(Arrays.toString(a[i]));
        }
    }

}

