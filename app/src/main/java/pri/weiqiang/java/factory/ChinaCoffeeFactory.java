package pri.weiqiang.java.factory;

import pri.weiqiang.java.factory.drink.Cappuccino;
import pri.weiqiang.java.factory.drink.Coffee;
import pri.weiqiang.java.factory.drink.Latte;

/**
 * 中国咖啡工厂
 *
 * @author Lsj
 */
public class ChinaCoffeeFactory extends CoffeeFactory {

    @Override
    public Coffee[] createCoffee() {
        // TODO Auto-generated method stub
        return new Coffee[]{new Cappuccino(), new Latte()};
    }

}
