package pri.weiqiang.java.factory;

import pri.weiqiang.java.factory.drink.Coffee;

/**
 * 定义一个抽象的咖啡工厂
 *
 * @author Lsj
 */
abstract class CoffeeFactory {

    /**
     * 生产可制造的咖啡
     *
     * @return
     */
    public abstract Coffee[] createCoffee();

}


