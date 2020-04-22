package pri.weiqiang.java.enumeration;

public class EnumDemo2 {

    public static void main(String[] args) {
        EnumTest test = EnumTest.TUE;

        //compareTo(E o)
        switch (test.compareTo(EnumTest.MON)) {
            case -1:
                System.out.println("TUE 在 MON 之前");
                break;
            case 1:
                System.out.println("TUE 在 MON 之后");
                break;
            default:
                System.out.println("TUE 与 MON 在同一位置");
                break;
        }

        //getDeclaringClass()
        System.out.println("getDeclaringClass(): " + test.getDeclaringClass().getName());

        //name() 和  toString()
        System.out.println("name(): " + test.name());
        System.out.println("toString(): " + test.toString());

        //ordinal()， 返回值是从 0 开始
        System.out.println("ordinal(): " + test.ordinal());
    }

    public enum EnumTest {
        MON, TUE, WED, THU, FRI, SAT, SUN;
    }
}
