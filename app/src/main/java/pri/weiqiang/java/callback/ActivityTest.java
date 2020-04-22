package pri.weiqiang.java.callback;

//https://www.cnblogs.com/zhaoyanjun/p/4569667.html
//https://blog.csdn.net/a_running_wolf/article/details/49359923/
// 不就是用接口来声明传入的参数吗，后面调用的时候再具体实现。就是被“回调”这个名字给蒙住了！
//回调的作用
//其实，回调函数就是在一个不确定实现的方法METHOD中用interface或者它的抽象方法留个口子，
// 留给具体调用者（调用前边那个不确定的方法METHOD）在调用时提供具体实现来补上那个口子。
// 从而达到更灵活地编码的目的，也大大减少了子类的使用。
public class ActivityTest {

    public static void main(String[] args) {
        TryButtonExample button = new TryButtonExample();
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void OnClick(TryButtonExample b) {
                System.out.println("clicked");
            }
        });
        button.click(); //user click,System call button.click();
    }
}
