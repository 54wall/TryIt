package pri.weiqiang.java.algorithm;

public class ReverseString {
    public static void main(String[] args) {
        //处理前后空格
        String str = "I just  do   it !".trim();
        //处理中间多空格
//        for (int i = 1; i <= 5; i++) {
//            String blankNum = "";
//            for (int j = 1; j <= i; j++) {
//                blankNum = blankNum + " ";
//            }
//            str = str.replaceAll(blankNum, " ");
//            System.out.println("str:"+str);
//        }
//        System.out.println("str:"+str);
        //存入数组中
        String[] splitString = str.split(" ");
        //装入StringBuffer中
        StringBuffer sbf = new StringBuffer();
        int b = 0;
        for (int i = splitString.length - 1; i >= 0; i--) {
            if (i == 0) {
                sbf.append(splitString[i]);
            } else {
                sbf.append(splitString[i]).append(" ");
            }
        }
        System.out.println(sbf);
    }
}

