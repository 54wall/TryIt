package pri.weiqiang.java.callback;

/**
 * 原来是命名为Button，但因为导入android项目在自动补全代码会优先导入本类，所以改为新名称
 * 这里就是为了解释以实际Button实现为举例，讲解java 回调
 */
public class TryButtonExample {

    OnClickListener listener;

    void click() {
        /*需要判断listener是否为null，本实例体现不出，对于有的实例，监听为可选项，
        监听可能没有设置，且监听设置在必然发生的过程*/

        if (listener != null) {
            listener.OnClick(this);
        }

    }


    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

}
