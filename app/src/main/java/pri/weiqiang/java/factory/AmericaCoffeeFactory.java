package pri.weiqiang.java.factory;

import pri.weiqiang.java.factory.drink.Americano;
import pri.weiqiang.java.factory.drink.Coffee;
import pri.weiqiang.java.factory.drink.Latte;

/**
 * 美国咖啡工厂
 *
 * @author Lsj
 */
public class AmericaCoffeeFactory extends CoffeeFactory {

    @Override
    public Coffee[] createCoffee() {
        // TODO Auto-generated method stub
        return new Coffee[]{new Americano(), new Latte()};
    }

}
