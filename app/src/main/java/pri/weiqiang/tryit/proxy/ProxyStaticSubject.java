package pri.weiqiang.tryit.proxy;

public class ProxyStaticSubject implements Subject {
   private Subject subject;
   public ProxyStaticSubject(Subject subject) {
      this.subject = subject;
   }
   @Override
   public void sayGoodBye() {
      //代理类，功能的增强
      System.out.println("ProxySubject sayGoodBye begin");
      //在代理类的方法中 间接访问被代理对象的方法
      subject.sayGoodBye();
      System.out.println("ProxySubject sayGoodBye end");
   }
   @Override
   public void sayHello(String str) {
      //代理类，功能的增强
      System.out.println("ProxySubject sayHello begin");
      //在代理类的方法中 间接访问被代理对象的方法
      subject.sayHello(str);
      System.out.println("ProxySubject sayHello end");
   }
}

