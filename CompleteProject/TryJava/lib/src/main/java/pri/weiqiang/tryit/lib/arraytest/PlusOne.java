package pri.weiqiang.tryit.lib.arraytest;

class PlusOne {
    public static void main(String[] args) {
        int[] digits = {9, 9, 9};
        int[] result = plusOne(digits);
        for (int i : result) {
            System.out.println("i:" + i);
        }
    }

    public static int[] plusOne(int[] digits) {
        boolean jinwei = false;
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] + 1 == 10) {
                digits[i] = 0;
                jinwei = true;
            } else {
                digits[i] = digits[i] + 1;
                jinwei = false;
                break;
            }
        }
        if (jinwei) {
            int[] newDigits = new int[digits.length + 1];
            newDigits[0] = 1;
            for (int i = 0; i < digits.length; i++) {
                newDigits[i + 1] = digits[i];
            }
            return newDigits;
        } else {
            return digits;
        }
    }
}
