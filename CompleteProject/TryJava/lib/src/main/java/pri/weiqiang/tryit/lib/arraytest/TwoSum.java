package pri.weiqiang.tryit.lib.arraytest;

class TwoSum {
    public static void main(String[] args){
        int[] nums = {1,7,6,11,15,3};
        int target = 9;
        twoSum(nums,target);
    }

    public static int[] twoSum(int[] nums,int target){
        int[] result = new int[2];
        for (int i = 0;i<nums.length-1;i++){
            for (int j=i+1;j<nums.length;j++){
                if(nums[i]+nums[j]==target){
                    result[0]=i;
                    result[1]=j;
                    System.out.println("result[0]:"+result[0]+",result[1]:"+result[1]);
                    return result;
                }
            }
        }
        return result;
    }
}
