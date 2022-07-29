package pri.weiqiang.tryit.lib.chartest;

class FirstUniqChar {

    public static void main(String[] args) {
        String s = "leetcode";
//        String s = "aabb";
//        String s = "dddccdbba";
        int result = firstUniqChar(s);
        System.out.println("result:" + result);
    }

    public static int firstUniqChar(String s) {

        char[] chars = new char[s.length()];
        int[] states = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            chars[i] = s.charAt(i);
            states[i] = 0;
        }

        int i = 0;
        boolean exist = false;
        int index = 0;
        for (; i < chars.length - 1; i++) {
            exist = false;
            for (int j = i + 1; j < chars.length; j++) {
//                System.out.println(",i:" + i + "chars[i]:" + chars[i] + ",j:" + j + "chars[j]:" + chars[j]);
                if (chars[i] == chars[j]) {
                    states[i] = 1;
                    states[j] = 1;
                    exist = true;
                }
            }

//            System.out.println("exist:" + exist + ",i:" + i + ",states[i]:" + states[i] + "，index:" + index);
            if (!exist && states[i] == 0) {
                index = i;
                break;
            } else if (i == chars.length - 1 && states[i] == 0) {
                index = i;
                break;
            }
        }

        if ((i == chars.length - 1) && states[i] == 1) {
            return -1;
        } else if (i == chars.length - 1 && states[i] == 0) {
            index = i;
        }
//        System.out.println("i:" + i + "，index:" + index);
//        System.out.println(chars[i]);
        return index;
    }
}
