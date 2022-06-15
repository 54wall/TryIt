package pri.weiqiang.tryit.lib.arraytest;

class MoveZeroes {
    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};
        moveZeroes(nums);

    }

    public static void moveZeroes(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length - count; i++) {
            if (nums[i] == 0) {
                for (int j = i; j < nums.length; j++) {
                    if (j == nums.length - 1) {
                        nums[j] = 0;
                    } else {
                        nums[j] = nums[j + 1];
                    }
                }
                count++;
                i--;
            }
        }

        for (int num : nums) {
            System.out.print(" " + num);
        }

    }
}
