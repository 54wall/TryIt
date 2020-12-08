package pri.weiqiang.java.auto;

public class AutoMakeJava {
    private static String TAG  = AutoMakeJava.class.getSimpleName();

    public static void main(String[] args) {
        System.out.println(TAG+"");
        Traveller dist = new Traveller();
        Traveller orig = new Traveller();
//        BeanUtils.copyProperties(dist,orig);
    }
}
