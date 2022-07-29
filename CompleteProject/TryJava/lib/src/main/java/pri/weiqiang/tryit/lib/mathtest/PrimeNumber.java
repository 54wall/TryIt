package pri.weiqiang.tryit.lib.mathtest;

import java.math.BigInteger;
import java.util.ArrayList;

class PrimeNumber {

    public static void main(String[] args) {
        int m = 2019;
        int[][] f = new int[2025][2025];
        ArrayList<Integer> p = new ArrayList<>();
        int o = 1;
        for (int i = 2; i < m; i++) {           //计算2019内的所有素数 并加入到列表
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    o = 2;
                    break;
                }
            }
            if (o == 1) {
                p.add(i);
            }
            o = 1;
        }
        f[0][0] = 1;
        for (int i = 1; i <= 306; i++) {           //用01背包计算数目
            for (int j = 0; j <= m; j++) {
                if (j < p.get(i - 1)) {
                    f[i][j] = f[i - 1][j];
                } else {
                    f[i][j] = f[i - 1][j - p.get(i - 1)] + f[i - 1][j];
                }
            }
        }
        BigInteger bigInteger = new BigInteger(String.valueOf(f[306][2019]));     //输出最后的结果
        System.out.println(bigInteger);
    }


}
