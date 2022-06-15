package pri.weiqiang.tryit.lib.arraytest;

class SingleNumber {

    public static void main(String[] args) {
        int[] nums = {2, 2, 1};
        int result = singleNumber(nums);
        System.out.println("result:" + result);
    }

    public static int singleNumber(int[] nums) {
        //初始化int数组全部是0
        int[] state = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    state[i] = 1;
                    state[j] = 1;

                }
            }
        }
        for (int i = 0; i < state.length; i++) {
            if (state[i] != 1) {
                return nums[i];
            }
        }
        return -1;
    }
}
