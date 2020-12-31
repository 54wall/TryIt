package pri.weiqiang.java.reference;

/* 这个类用于申请堆内存 */
public class Memory {
    private final byte[] alloc;

    public Memory(int size) {
        this.alloc = new byte[size];
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("被GC回收");
    }
}