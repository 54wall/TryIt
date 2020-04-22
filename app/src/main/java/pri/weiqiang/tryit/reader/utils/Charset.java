package pri.weiqiang.tryit.reader.utils;

/**
 * 编码类型
 */
public enum Charset {
    UTF8("UTF-8"),
    UTF16LE("UTF-16LE"),
    UTF16BE("UTF-16BE"),
    GBK("GBK");

    public static final byte BLANK = 0x0a;
    private String mName;

    private Charset(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
