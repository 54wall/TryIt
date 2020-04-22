package pri.weiqiang.java.factory;

import pri.weiqiang.java.factory.drink.Coffee;

/**
 * 工厂方法测试
 *
 * @author Lsj
 */
public class FactoryMethodTest {

    private static void print(Coffee[] c) {
        for (Coffee coffee : c) {
            System.out.println(coffee.getName());
        }
    }

    public static void main(String[] args) {
        CoffeeFactory chinaCoffeeFactory = new ChinaCoffeeFactory();
        Coffee[] chinaCoffees = chinaCoffeeFactory.createCoffee();
        System.out.println("中国咖啡工厂可以生产的咖啡有：");
        print(chinaCoffees);
        CoffeeFactory americaCoffeeFactory = new AmericaCoffeeFactory();
        Coffee[] americaCoffees = americaCoffeeFactory.createCoffee();
        System.out.println("美国咖啡工厂可以生产的咖啡有：");
        print(americaCoffees);
    }
}
