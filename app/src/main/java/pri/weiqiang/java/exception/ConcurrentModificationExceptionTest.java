package pri.weiqiang.java.exception;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * https://www.cnblogs.com/zhuyeshen/p/10956822.html
 */
public class ConcurrentModificationExceptionTest {

    public static void main(String[] args) {
//        test();
//        testThread();
        test6();
    }

    public static void test() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(2);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer == 2)
                list.remove(integer);
        }
    }

    public static void testFix() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(2);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer == 2)
                iterator.remove();  //注意这个地方
        }
    }


    public static void testThread() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Thread thread1 = new Thread(){
            public void run() {
                Iterator<Integer> iterator = list.iterator();
                while(iterator.hasNext()){
                    Integer integer = iterator.next();
                    System.out.println(integer);
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        };
        Thread thread2 = new Thread(){
            public void run() {
                Iterator<Integer> iterator = list.iterator();
                while(iterator.hasNext()){
                    Integer integer = iterator.next();
                    System.out.println(integer);
                    if(integer==2)
                        iterator.remove();
                }
            }
        };
        thread1.start();
        thread2.start();
    }

    public static void testThread2() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Thread thread1 = new Thread(){
            public void run() {
                Iterator<Integer> iterator = list.iterator();
                while(iterator.hasNext()){
                    Integer integer = iterator.next();
                    System.out.println(integer);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread2 = new Thread(){
            public void run() {
                //使用synchronized将list
                synchronized(list) {
                    Iterator<Integer> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        Integer integer = iterator.next();
                        System.out.println(integer);
                        if (integer == 2)
                            iterator.remove();
                    }
                }
            }
        };
        thread1.start();
        thread2.start();
    }


    public static void test6() {
        List<Integer> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(Integer.valueOf(i));
        }

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ListIterator<Integer> iterator = list.listIterator();
                while (iterator.hasNext()) {
                    System.out.println("thread1 " + iterator.next().intValue());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Integer integer : list) {
                    System.out.println("thread2 " + integer.intValue());
                    if (integer.intValue() == 5) {
                        list.remove(integer);
                    }
                }
                for (Integer integer : list) {
                    System.out.println("thread2 again " + integer.intValue());
                }
//                ListIterator<Integer> iterator = list.listIterator();
//                while (iterator.hasNext()) {
//                    Integer integer = iterator.next();
//                    System.out.println("thread2 " + integer.intValue());
//                    if (integer.intValue() == 5) {
//                        iterator.remove();
//                    }
//                }
            }
        });
        thread1.start();
        thread2.start();
    }
}
