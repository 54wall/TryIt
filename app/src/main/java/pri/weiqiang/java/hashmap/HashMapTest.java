package pri.weiqiang.java.hashmap;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {
    private Integer a;

    private HashMapTest(int a) {
        this.a = a;
    }

    public static void main(String[] args) {
        Map<HashMapTest, Integer> map = new HashMap<HashMapTest, Integer>();
        HashMapTest instance = new HashMapTest(1);
        System.out.println("instance.hashcode:" + instance.hashCode());
        HashMapTest newInstance = new HashMapTest(1);
        System.out.println("newInstance.hashcode:" + newInstance.hashCode());
        map.put(instance, 1);
        map.put(newInstance, 2);
        Integer value = map.get(instance);
        System.out.println("instance value:" + value);
        Integer value1 = map.get(newInstance);
        System.out.println("newInstance value:" + value1);
        System.out.println("equal:" + instance.equals(newInstance));

        String string = "AB";
        String string2 = "ABC";
        string.hashCode();
//        string.hashCode();
//        System.out.println(string.equals(string2));

    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof HashMapTest)) {
            return false;
        } else {
            HashMapTest other = (HashMapTest) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Integer this$data = this.getA();
                Integer other$data = other.getA();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    private boolean canEqual(Object other) {
        return other instanceof HashMapTest;
    }

    private Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

//    public int hashCode() {
//        boolean PRIME = true;
//        byte result = 1;
//        Integer $data = this.getA();
//        int result1 = result * 59 + ($data == null?43:$data.hashCode());
//        return result1;
//    }
}
//运行结果：
//instance.hashcode:60
//newInstance.hashcode:60
//instance value:2
//newInstance value:2

//作者：程序员的散漫生活
//链接：https://www.jianshu.com/p/75d9c2c3d0c1
//來源：简书
//简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。