package pri.weiqiang.java.factory;

import pri.weiqiang.java.factory.drink.CocaCola;
import pri.weiqiang.java.factory.drink.Coffee;
import pri.weiqiang.java.factory.drink.Latte;
import pri.weiqiang.java.factory.drink.Sodas;
import pri.weiqiang.java.factory.drink.Tea;

/**
 * 美国饮品制造工厂
 * 制造咖啡和碳酸饮料
 *
 * @author Lsj
 */
public class AmericaDrinksFactory implements AbstractDrinksFactory {

    @Override
    public Coffee createCoffee() {
        // TODO Auto-generated method stub
        return new Latte();
    }

    @Override
    public Tea createTea() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Sodas createSodas() {
        // TODO Auto-generated method stub
        return new CocaCola();
    }

}
