package pri.weiqiang.java.factory;

import pri.weiqiang.java.factory.drink.Coffee;
import pri.weiqiang.java.factory.drink.Drink;
import pri.weiqiang.java.factory.drink.Sodas;
import pri.weiqiang.java.factory.drink.Tea;

/**
 * 抽象工厂测试类
 *
 * @author Lsj
 * https://www.cnblogs.com/carryjack/p/7709861.html
 */
public class AbstractFactoryTest {

    private static void print(Drink drink) {
        if (drink == null) {
            System.out.println("产品：--");
        } else {
            System.out.println("产品：" + drink.getName());
        }
    }

    public static void main(String[] args) {
        AbstractDrinksFactory chinaDrinksFactory = new ChinaDrinksFactory();
        Coffee coffee = chinaDrinksFactory.createCoffee();
        Tea tea = chinaDrinksFactory.createTea();
        Sodas sodas = chinaDrinksFactory.createSodas();
        System.out.println("中国饮品工厂有如下产品：");
        print(coffee);
        print(tea);
        print(sodas);

        AbstractDrinksFactory americaDrinksFactory = new AmericaDrinksFactory();
        coffee = americaDrinksFactory.createCoffee();
        tea = americaDrinksFactory.createTea();
        sodas = americaDrinksFactory.createSodas();
        System.out.println("美国饮品工厂有如下产品：");
        print(coffee);
        print(tea);
        print(sodas);
    }

}
