package pri.weiqiang.java.Iterator;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * https://www.cnblogs.com/loong-hon/p/10256686.html
 * java.util.ConcurrentModificationException异常原因及解决方法
 */
public class IteratorDemo01 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        List<Integer> listA=new ArrayList<>();
        listA.add(1);
        listA.add(2);
        listA.add(3);
        listA.add(4);
        listA.add(5);
        listA.add(6);

        for(Integer a:listA){
            if (a==3) {
                listA.remove(3);
            }
        }
    }

    public static void main2(String[] args) {
        // TODO Auto-generated method stub
        List<Integer> listA=new ArrayList<>();
        listA.add(1);
        listA.add(2);
        listA.add(3);
        listA.add(4);
        listA.add(5);
        listA.add(6);
        Iterator<Integer> it_b=listA.iterator();
        while(it_b.hasNext()){
            Integer a=it_b.next();
            if (a==4) {
                it_b.remove();
            }
        }
        for(Integer b:listA){
            System.out.println(b);
        }
    }
}