package pri.weiqiang.tryit.lib.chartest;

public class IsPalindrome {

    public static void main(String[] args) {
        String s = "A man, a plan ,a canal:Panama";
//      String s = "A a y x y a A";
//        String s = "0P";
        System.out.println("isPalindrome(s):" + isPalindrome(s));
    }

    public static boolean isPalindrome(String s) {
        char[] charS = s.toLowerCase().toCharArray();
        int j = s.length() - 1;
        for (int i = 0; i < charS.length && i < j; i++) {
            if (charS[i] != ' ' && Character.isLetterOrDigit(charS[i])) {
                for (; j >= i; j--) {
                    if (charS[j] != ' ' && Character.isLetterOrDigit(charS[j])) {
//                        System.out.println("22 i:" + i + ",j:" + j + "charS[i]:" + charS[i] + ",charS[j]:" + charS[j]);
                        if (charS[i] == charS[j]) {
                            break;
                        } else {
//                            System.out.println("i:" + i + ",j:" + j + "charS[i]:" + charS[i] + ",charS[j]:" + charS[j]);
                            return false;
                        }

                    }
                }
                j--;
            }
        }
        return true;
    }
}
