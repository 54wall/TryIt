package pri.weiqiang.java.factory.drink;

/**
 * 拿铁、美式咖啡、卡布奇诺等均为咖啡家族的一种产品
 * 咖啡则作为一种抽象概念
 *
 * @author Lsj
 */
public abstract class Coffee extends Drink {

    /**
     * 获取coffee名称
     *
     * @return
     */
    public abstract String getName();

}


