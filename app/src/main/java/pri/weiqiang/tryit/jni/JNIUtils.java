package pri.weiqiang.tryit.jni;

class JNIUtils {
    static {
        System.loadLibrary("JNIHello");
    }

    public native static String sayHelloFromJNI();
}
