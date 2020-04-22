package pri.weiqiang.java.factory;


import pri.weiqiang.java.factory.drink.Coffee;
import pri.weiqiang.java.factory.drink.Sodas;
import pri.weiqiang.java.factory.drink.Tea;

/**
 * 抽象的饮料产品家族制造工厂
 *
 * @author Lsj
 */
interface AbstractDrinksFactory {

    /**
     * 制造咖啡
     *
     * @return
     */
    Coffee createCoffee();

    /**
     * 制造茶
     *
     * @return
     */
    Tea createTea();

    /**
     * 制造碳酸饮料
     *
     * @return
     */
    Sodas createSodas();
}


