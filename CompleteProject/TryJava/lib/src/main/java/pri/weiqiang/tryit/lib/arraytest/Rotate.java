package pri.weiqiang.tryit.lib.arraytest;

class Rotate {
    public static void main(String args[]){
        int[] nums = {1,2,3,4,5,6,7};
        //1,2,3,4,5,6,7
        //7,1,2,3,4,5,6
        int k = 3;
        //输出5,6,7,1,2,3,4
        rotate(nums,k);
    }
    public static void rotate(int[] nums,int k){

        k = k % nums.length;
        for (int i = 0; i < k; i++) {
            int lastIndex = nums.length-1;
            int temp = nums[lastIndex];
            for (int j = lastIndex; j >= 1; j--) {
                System.out.println("j:"+j);
                nums[j] = nums[j-1];
                if(j==1){
                    nums[j-1] = temp;
                }
            }
        }

        for (int i:nums) {
            System.out.println(i+",");
        }
    }
}
