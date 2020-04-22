package pri.weiqiang.java.generic;

/**
 * http://www.cnblogs.com/coprince/p/8603492.html
 */
public class GenericDemo {
    public static void main(String[] args) {

        example3();

    }

    public static void example1() {
        //泛型的类型参数只能是类类型（包括自定义类），不能是简单类型
//传入的实参类型需与泛型的类型参数类型相同，即为Integer.
        Generic<Integer> genericInteger = new Generic<Integer>(123456);

//传入的实参类型需与泛型的类型参数类型相同，即为String.
        Generic<String> genericString = new Generic<String>("key_vlaue");
        System.out.println("泛型测试" + "key is " + genericInteger.getKey());
        System.out.println("泛型测试" + "key is " + genericString.getKey());
    }

    public static void example2() {

        Generic generic = new Generic("111111");
        Generic generic1 = new Generic(4444);
        Generic generic2 = new Generic(55.55);
        Generic generic3 = new Generic(false);

        System.out.println("泛型测试" + "key is " + generic.getKey());
        System.out.println("泛型测试" + "key is " + generic1.getKey());
        System.out.println("泛型测试" + "key is " + generic2.getKey());
        System.out.println("泛型测试" + "key is " + generic3.getKey());
    }

    private static void example3() {
        Generic<Integer> gInteger = new Generic<Integer>(123);
        Generic<Number> gNumber = new Generic<Number>(456);

        showKeyValue(gNumber);

// showKeyValue这个方法编译器会为我们报错：Generic<java.lang.Integer>
// cannot be applied to Generic<java.lang.Number>
// showKeyValue(gInteger);
        showKeyValue1(gNumber);
        showKeyValue1(gInteger);
    }

    private static void showKeyValue(Generic<Number> obj) {
        System.out.println("泛型测试" + "key value is " + obj.getKey());
    }

    private static void showKeyValue1(Generic<?> obj) {
        System.out.println("泛型测试" + "key value is " + obj.getKey());
    }
}
