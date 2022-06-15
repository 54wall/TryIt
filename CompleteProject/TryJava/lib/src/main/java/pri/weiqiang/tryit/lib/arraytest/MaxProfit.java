package pri.weiqiang.tryit.lib.arraytest;

class MaxProfit {
    public static void main(String[] args){
        int[] prices = {7,1,5,3,6,4};
        maxProfit(prices);
    }
    //可以当天买，当天卖
    public static int maxProfit(int[] prices){
        int max = 0;
        for(int i =1;i<prices.length;i++){
            if(prices[i]>prices[i-1]){
                max = max+(prices[i]-prices[i-1]);
            }
        }
        System.out.println("max:"+max);
        return max;
    }
}
