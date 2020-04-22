package pri.weiqiang.tryit.progressbar;

/**
 * 模拟网络环境
 * Name: NetOperator
 * Author: liuan
 * creatTime:2017-01-11 11:32
 */

class NetOperator {
    public void opeartor() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

