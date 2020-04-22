package pri.weiqiang.java.hashmap;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest0 {
    private Integer a;

    private HashMapTest0(int a) {
        this.a = a;
    }

    public static void main(String[] args) {
        Map<HashMapTest0, Integer> map = new HashMap<HashMapTest0, Integer>();
        HashMapTest0 instance = new HashMapTest0(1);
        HashMapTest0 newInstance = new HashMapTest0(1);
        map.put(instance, 1);
        map.put(newInstance, 2);
        Integer value = map.get(instance);
        System.out.println("instance value:" + value);
        Integer value1 = map.get(newInstance);
        System.out.println("newInstance value:" + value1);

    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof HashMapTest0)) {
            return false;
        } else {
            HashMapTest0 other = (HashMapTest0) o;
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
        return other instanceof HashMapTest0;
    }

    private Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }
}
//运行结果：
//instance value:1
//newInstance value:2