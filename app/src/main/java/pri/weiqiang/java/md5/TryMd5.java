package pri.weiqiang.java.md5;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TryMd5 {

    public static void main(String[] args) throws Exception {
//        tryMd5One();

        crypt("s878926199a");
        getMD5Str("s878926199a");
    }

    private static void tryMd5One() throws NoSuchAlgorithmException, IOException {
        MessageDigest degest = MessageDigest.getInstance("MD5");
        FileInputStream inputStream = new FileInputStream("D:\\Develop\\周报.txt");
        //对于大文件或者网络文件使用输入流的形式要比字节数组方便很多也节省内存
        DigestInputStream dis = new DigestInputStream(inputStream, degest);
        byte[] buffer = new byte[8986];
        ByteArrayOutputStream fileOutput = new ByteArrayOutputStream();
        while ((dis.read(buffer)) != -1) {
            //跟读普通输入流是一样的，原理就是需要将输入流读完后，再调用digest方法才能获取整个文件的MD5
            try {
                fileOutput.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        byte[] sumary = degest.digest();
        StringBuffer strBuffer = new StringBuffer();
        for (int i = 0; i < sumary.length; i++) {
            String tmp = Integer.toHexString(sumary[i] & 0xff);
            //如果这个字节的值小于16，那么转换的就只有一个字符，所以需要手动添加一个字符“0”，
            if (tmp.length() == 1) {
                tmp = "0" + tmp;
            }
            strBuffer.append(tmp);
        }
        System.out.println(strBuffer.toString());
    }

    //https://www.cnblogs.com/pcheng/p/7724863.html
    public static String getMD5Three(String path) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi.toString(16);
    }

    //blog.csdn.net/wangqiuyun/article/details/22941433
    /*楼主，你这段程序BUG一大堆，首先如一楼所说，MappedByteBuffer无法释放资源，其二如二楼所说生成的MD5码中如果首位是0的话，会被自动干掉。
     * */
    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public static String getMd5ByDigestUtils(String path) throws FileNotFoundException {
        //在这个包里边packageorg.apache.commons.codec.digest;
//        DigestUtils.md5Hex(new FileInputStream(path));
        return "null";
    }

    /**
     * 原文：https://blog.csdn.net/u012660464/article/details/78759296
     */
    public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String s = hexString.toString();
        System.out.println("crypt:" + s);
        return s;
    }

    /**
     * https://blog.csdn.net/u012660464/article/details/78759296
     */
    public static String getMD5Str(String str) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            System.out.println("getMD5Str:" + new BigInteger(1, md.digest()).toString(16));
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误，" + e.toString());
        }
    }


}
