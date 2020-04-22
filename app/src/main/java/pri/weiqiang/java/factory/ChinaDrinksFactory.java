package pri.weiqiang.java.factory;

import pri.weiqiang.java.factory.drink.Coffee;
import pri.weiqiang.java.factory.drink.Latte;
import pri.weiqiang.java.factory.drink.MilkTea;
import pri.weiqiang.java.factory.drink.Sodas;
import pri.weiqiang.java.factory.drink.Tea;

/**
 * 中国饮品工厂
 * 制造咖啡与茶
 *
 * @author Lsj
 */
public class ChinaDrinksFactory implements AbstractDrinksFactory {

    @Override
    public Coffee createCoffee() {
        // TODO Auto-generated method stub
        return new Latte();
    }

    @Override
    public Tea createTea() {
        // TODO Auto-generated method stub
        return new MilkTea();
    }

    @Override
    public Sodas createSodas() {
        // TODO Auto-generated method stub
        return null;
    }

}
