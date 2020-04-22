package pri.weiqiang.tryit.main;

/**
 * @author vondear
 * @date 2016/11/13
 */

public class ModelMainItem {

    private String name;

    private Class activity;

    public ModelMainItem(String name, Class activity) {
        this.name = name;
        this.activity = activity;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
