package pri.weiqiang.tryit.lib.arraytest;

class RemoveDuplicate {
    public static void main(String[] args) {
        int[] a = {1, 1, 2};
//        System.out.println("length:"+removeDuplicates(a));
        removeDuplicates2(a);
    }

    //使用额外空间
    public static int removeDuplicates(int[] nums) {
        int[] b = new int[nums.length];
        b[0] = nums[0];
        int x = 0;
        for (int i = 0; i < nums.length; i++) {
            boolean isExists = false;
            for (int j = 0; j < x; j++) {
                if (b[j] == nums[i]) {
                    isExists = true;
                }
            }
            if (!isExists) {
                b[x] = nums[i];
                x++;
            }

        }
         nums = new int[x];
        for (int m=0;m<x;m++) {
            nums[m]=b[m];
            System.out.println(nums[m]);
        }

        return nums.length;
    }

    /**
     *不使用额外空间
     */
    public static int removeDuplicates2(int[] nums) {
        int p =0;
        int q =1;
        while(q<nums.length){
            if(nums[p]!=nums[q]){
                nums[p+1] = nums[q];
                p++;
            }
            q++;
        }

        for (int m=0;m<nums.length;m++) {
            System.out.println(nums[m]);
        }
        return p+1;
    }
}
