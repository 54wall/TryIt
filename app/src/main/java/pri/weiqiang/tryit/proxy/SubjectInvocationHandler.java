package pri.weiqiang.tryit.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * https://blog.csdn.net/weixin_39079048/article/details/98852947
 *
 *     编译时就确定了被代理的类是哪一个，那么就可以直接使用静态代理；
 *     运行时才确定被代理的类是哪个，那么可以使用类动态代理。
 */
public class SubjectInvocationHandler implements InvocationHandler {
   //这个就是我们要代理的真实对象
   private Object subject;
   //构造方法，给我们要代理的真实对象赋初值
   public SubjectInvocationHandler(Object subject) {
      this.subject = subject;
   }
   @Override
   public Object invoke(Object object, Method method, Object[] args) throws Throwable {
      //在代理真实对象前我们可以添加一些自己的操作
      System.out.println("before Method invoke");
      System.out.println("Method:" + method);
      //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
      method.invoke(subject, args);
      //在代理真实对象后我们也可以添加一些自己的操作
      System.out.println("after Method invoke");
      return null;
   }
}

