package pri.weiqiang.tryit.annotation;

/**
 * 作者：frank909
 * 来源：CSDN
 * 原文：https://blog.csdn.net/briblue/article/details/73824058
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */

class NoBug {

    @Jiecha
    public void suanShu() {
        System.out.println("1234567890");
    }

    @Jiecha
    public void jiafa() {
        System.out.println("1+1=" + 1 + 1);
    }

    @Jiecha
    public void jiefa() {
        System.out.println("1-1=" + (1 - 1));
    }

    @Jiecha
    public void chengfa() {
        System.out.println("3 x 5=" + 3 * 5);
    }

    @Jiecha
    public void chufa() {
        System.out.println("6 / 0=" + 6 / 0);
    }

    public void ziwojieshao() {
        System.out.println("我写的程序没有 bug!");
    }

}

