package pri.weiqiang.java.hashmap;

import java.util.HashMap;
import java.util.Map;

public class SameKeyHashMap {

    public static void main(String[] args) {
        Map<A, String> map = new HashMap<A, String>();
        A a1 = new A();
        a1.setCode("123");
        a1.setName("456");
        map.put(a1, "test1");
        System.out.println(map);
        a1.setCode("789");
        map.put(a1, "test2");
        map.put(null, "nullTest");
//        map.put(null,null);
        System.out.println(map);
        System.out.println("根据关键值遍历value");

        for (A key : map.keySet()) {

            System.out.println(map.get(key));
        }
    }

    static class A {
        private String code;
        private String name;

        @Override
        public int hashCode() {
            // TODO Auto-generated method stub
            return code.hashCode() + name.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            return true;
        }

        public String getCode() {
            return code;
        }

        void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return code.toString() + name.toString();
        }

    }

}
