package pri.weiqiang.java.factory;

import pri.weiqiang.java.factory.drink.Americano;
import pri.weiqiang.java.factory.drink.Cappuccino;
import pri.weiqiang.java.factory.drink.Coffee;
import pri.weiqiang.java.factory.drink.Drink;
import pri.weiqiang.java.factory.drink.Latte;

/**
 * 简单工厂--用于创建不同类型的咖啡实例
 *
 * @author Lsj
 */
public class SimpleFactory {

    /**
     * 通过类型获取Coffee实例对象
     *
     * @param type 咖啡类型
     * @return
     */
    private static Coffee createInstance(String type) {
        if ("americano".equals(type)) {
            return new Americano();
        } else if ("cappuccino".equals(type)) {
            return new Cappuccino();
        } else if ("latte".equals(type)) {
            return new Latte();
        } else {
            throw new RuntimeException("type[" + type + "]类型不可识别，没有匹配到可实例化的对象！");
        }
    }

    public static void main(String[] args) {
        Coffee latte = SimpleFactory.createInstance("latte");
        System.out.println("创建的咖啡实例为:" + latte.getName());
        Coffee cappuccino = SimpleFactory.createInstance("cappuccino");
        System.out.println("创建的咖啡实例为:" + cappuccino.getName());
        Drink drink = new Drink() {
            @Override
            public String getName() {
                return null;
            }
        };
        drink = latte;
        System.out.println("drink:"+drink.getName());
    }

}