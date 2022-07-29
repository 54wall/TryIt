package pri.weiqiang.tryit.proxy;

import androidx.appcompat.app.AppCompatActivity;
import pri.weiqiang.tryit.R;

import android.os.Bundle;

import java.lang.reflect.Proxy;

public class proxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        /**
         * 动态代理
         */
        //被代理类
        Subject realSubject = new RealSubject2();
        //我们要代理哪个类，就将该对象传进去，最后是通过该被代理对象来调用其方法的
        SubjectInvocationHandler handler = new SubjectInvocationHandler(realSubject);
        //生成代理类
        Subject subject = (Subject) Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                realSubject.getClass().getInterfaces(), handler);
        //输出代理类对象
        System.out.println("Proxy : "+ subject.getClass().getName());
        System.out.println("Proxy super : "+ subject.getClass().getSuperclass().getName());
        System.out.println("Proxy interfaces : "+ subject.getClass().getInterfaces()[0].getName());
        //调用代理类sayGoodBye方法
        subject.sayGoodBye();
        System.out.println("--------");
        //调用代理类sayHello方法
        subject.sayHello("Test");

        /**
         * 静态代理
         */
        //被代理的对象，某些情况下 我们不希望修改已有的代码，我们采用代理来间接访问
//        RealSubject realSubject = new RealSubject();
        //代理类对象
        ProxyStaticSubject proxySubject = new ProxyStaticSubject(realSubject);
        //调用代理类对象的方法
        proxySubject.sayGoodBye();
        System.out.println("******");
        proxySubject.sayHello("Test");

    }
}