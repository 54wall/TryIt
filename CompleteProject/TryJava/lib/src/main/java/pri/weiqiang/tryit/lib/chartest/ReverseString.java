package pri.weiqiang.tryit.lib.chartest;

class ReverseString {
    public static void main(String[] args) {
        char[] s = {'h', 'e', 'l', 'l', 'o'};
        reverseString(s);
    }

    public static void reverseString(char[] s) {
        char temp;
        for (int i = 0; i <= s.length / 2 - 1; i++) {
            temp = s[i];
            s[i] = s[s.length - i - 1];
            s[s.length - i - 1] = temp;
        }
        for (char c : s) {
            System.out.print(" " + c);
        }
    }
}
