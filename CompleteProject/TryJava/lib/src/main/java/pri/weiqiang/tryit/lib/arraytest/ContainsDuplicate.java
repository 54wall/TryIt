package pri.weiqiang.tryit.lib.arraytest;

class ContainsDuplicate {
    public static void main(String[] args){
        int[] nums = {1,2,3,1};
        boolean isExits = containsDuplicate(nums);
        System.out.println("isExits:"+isExits);
    }

    public static boolean containsDuplicate(int[] nums){
        for (int i=0;i<nums.length;i++){
            int temp = nums[i];
            for (int j=i+1;j<nums.length;j++){
                if(temp==nums[j]){
                    return true;
                }
            }
        }
        return false;
    }
}
